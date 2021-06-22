package com.tuya.iot.suite.service.asset.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.connector.open.api.model.PageResult;
import com.tuya.iot.suite.ability.asset.ability.AssetAbility;
import com.tuya.iot.suite.ability.asset.model.*;
import com.tuya.iot.suite.ability.idaas.model.IdaasUser;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.model.PageDataVO;
import com.tuya.iot.suite.core.util.SimpleConvertUtil;
import com.tuya.iot.suite.service.asset.AssetService;
import com.tuya.iot.suite.service.device.DeviceService;
import com.tuya.iot.suite.service.dto.AssetConvertor;
import com.tuya.iot.suite.service.dto.AssetDTO;
import com.tuya.iot.suite.service.dto.DeviceDTO;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tuya.iot.suite.core.constant.ErrorCode.USER_NOT_AUTH;


/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/10
 */
@Service
@Slf4j
public class AssetServiceImpl implements AssetService {
    private static final int PAGE_NO = 1;
    private static final int PAGE_SIZE = 100;

    private LoadingCache<String, AssetDTO> ASSET_CACHE = Caffeine
            .newBuilder()
            .expireAfterWrite(Duration.ofMillis(10 * 60 * 1000))
            .build(
                    assetId -> {
                        AssetDTO root = new AssetDTO();
                        root.setAsset_id(assetId);
                        root.setSubAssets(getTree(assetId));
                        return root;
                    }
            );

    @Resource
    private AssetAbility assetAbility;

    @Resource
    private DeviceService deviceService;
    @Resource
    private UserService userService;
    @Value("${asset.auth.size:40}")
    private Integer assetAuthSize;

    @Override
    public Response addAsset(String spaceId, String assetName, String parentAssetId, String userId) {
        //Don't need check asset authorization if the parentAssetId is blank
        if (!StringUtils.isEmpty(parentAssetId)) {
            checkAssetAuthOfUser(parentAssetId, userId);
        }
        AssetAddRequest request = new AssetAddRequest();
        request.setName(assetName);
        request.setParent_asset_id(parentAssetId);
        String assetId = assetAbility.addAsset(request);
        authorizedAssetWithoutToChildren(spaceId, assetId, userId);
        // addAssetToCache(assetId, request, false);
        return new Response(assetId);
    }


    /**
     * 对资产授权
     *
     * @param assetId
     * @param userId
     */
    private void authorizedAssetWithoutToChildren(String spaceId, String assetId, String userId) {
        log.info("开始资产[{}]授权给用户[{}]", assetId, userId);
        AssetAuthorizationRequest assetAuthorizationRequest = new AssetAuthorizationRequest(userId, assetId, false);
        Boolean result = assetAbility.authorized(assetAuthorizationRequest);
        if (Objects.isNull(result) || !result.booleanValue()) {
            log.info("资产[{}]授权给用户[{}]失败", assetId, userId);
        } else {
            log.info("资产[{}]授权给用户[{}]成功", assetId, userId);
        }
        //给系统管理员授权
        List<IdaasUser> idaasUsers = userService.queryAdmins(spaceId);
        if (!CollectionUtils.isEmpty(idaasUsers)) {
            for (IdaasUser idaasUser : idaasUsers) {
                assetAuthorizationRequest.setUid(idaasUser.getUid());
                result = result && assetAbility.authorized(assetAuthorizationRequest);
            }
        }
    }

    @Override
    public Boolean grantAllAssetToAdmin(String spaceId) {
        boolean result = true;
        //给系统管理员授权
        List<IdaasUser> idaasUsers = userService.queryAdmins(spaceId);
        if (!CollectionUtils.isEmpty(idaasUsers)) {
            for (IdaasUser idaasUser : idaasUsers) {
                result = result && grantAllAsset(idaasUser.getUid());
            }
        }
        return result;
    }


    @Override
    public Response updateAsset(String userId, String assetId, String assetName) {
        checkAssetAuthOfUser(assetId, userId);
        AssetModifyRequest request = new AssetModifyRequest();
        request.setName(assetName);
        Boolean aBoolean = assetAbility.modifyAsset(assetId, request);
        // updateAssetToCache(assetId, assetName);
        return new Response(aBoolean);
    }


    private void checkAssetAuthOfUser(String assetId, String userId) {
        List<String> authList = listAuthorizedAssetIds(userId);
        if (!StringUtils.isEmpty(assetId) && !CollectionUtils.contains(authList.listIterator(), assetId)) {
            log.info("资产[{}]未授权给用户[{}]", assetId, userId);
            throw new ServiceLogicException(USER_NOT_AUTH);
        }
    }

    @Override
    public Response deleteAsset(String userId, String assetId) {
        checkAssetAuthOfUser(assetId, userId);
        Boolean aBoolean = assetAbility.deleteAsset(assetId);
        // deleteAssetFromCache(assetId);
        return new Response(aBoolean);
    }

    /**
     * 手动删除cache中的的数据
     *
     * @param assetId
     */
    private void deleteAssetFromCache(String assetId) {
        AssetDTO son = searchFromCache(assetId);
        AssetDTO assetDTO = searchFromCache(checkTopAsset(son.getParent_asset_id()));
        List<AssetDTO> subAssets = assetDTO.getSubAssets();
        subAssets.remove(son);

    }

    private String checkTopAsset(String parent_asset_id) {
        if (StringUtils.isEmpty(parent_asset_id)) {
            return "-1";
        }
        return parent_asset_id;
    }

    /**
     * 手动给缓存添加最新的资产
     *
     * @param assetId
     * @param request
     */
    private boolean addAssetToCache(String assetId, AssetAddRequest request, boolean refreshDevice) {
        AssetDTO assetDTO = searchFromCache(checkTopAsset(request.getParent_asset_id()));
        //判断父级节点是否能找到，找不到则未添加成功
        if (!request.getParent_asset_id().equals(assetDTO.getAsset_id())) {
            return false;
        }
        AssetDTO son = searchFromCache(assetId);
        //tree中已经包含了该节点，不做其他处理 了
        if (assetId.equals(son.getAsset_id())) {
            return true;
        }
        son = new AssetDTO();
        son.setAsset_id(assetId);
        son.setChild_asset_count(0);
        son.setAsset_name(request.getName());
        son.setParent_asset_id(request.getParent_asset_id());
        if (refreshDevice) {
            int childDeviceCount = getChildDeviceIdsBy(assetId).size();
            son.setChild_device_count(childDeviceCount);
        } else {
            son.setChild_device_count(0);
        }
        son.setAsset_full_name(assetDTO.getAsset_full_name() + request.getName());
        son.setSubAssets(new ArrayList<>());
        son.setLevel(assetDTO.getLevel() + 1);
        List<AssetDTO> subAssets = assetDTO.getSubAssets();
        if (subAssets == null) {
            subAssets = new ArrayList<>();
        }
        subAssets.add(son);
        assetDTO.setSubAssets(subAssets);
        assetDTO.setChild_asset_count(subAssets.size());
        return true;
    }

    /**
     * 手动给缓存更新的资产
     *
     * @param assetId
     * @param assetName
     */
    private void updateAssetToCache(String assetId, String assetName) {
        AssetDTO assetDTO = searchFromCache(assetId);
        assetDTO.setAsset_full_name(assetDTO.getAsset_full_name()
                .substring(0, assetDTO.getAsset_full_name().length() - assetDTO.getAsset_name().length()) + assetName);
        assetDTO.setAsset_name(assetName);
        ASSET_CACHE.refresh("-1");
    }

    /**
     * 从cache中找到该节点
     *
     * @param assetId
     * @return
     */
    private AssetDTO searchFromCache(String assetId) {
        AssetDTO assetDTO = ASSET_CACHE.get("-1");
        AssetDTO temp = searchFromTree(assetId, assetDTO);
        if (temp != null) {
            return temp;
        }
        return new AssetDTO();
    }

    private AssetDTO searchFromTree(String assetId, AssetDTO assetDTO) {
        if (assetDTO == null) {
            return assetDTO;
        }
        if (assetId.equals(assetDTO.getAsset_id())) {
            return assetDTO;
        }
        if (!CollectionUtils.isEmpty(assetDTO.getSubAssets())) {
            for (AssetDTO subAsset : assetDTO.getSubAssets()) {
                AssetDTO temp = searchFromTree(assetId, subAsset);
                if (temp != null) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * 跟进用户ID 获取授权后的资产
     *
     * @param userId
     * @return
     */
    private List<String> listAuthorizedAssetIds(String userId) {
        log.info("查询用户userId[{}]授权的资产IDs", userId);
        List<String> authorizedAssetIds = new ArrayList<>();

        boolean hasMore = true;
        int pageNo = PAGE_NO;
        while (hasMore) {
            PageResultWithTotal<AuthorizedAsset> pageResult = assetAbility.pageListAuthorizedAssets(userId, pageNo, PAGE_SIZE);
            hasMore = pageResult.getHasMore().booleanValue();
            List<AuthorizedAsset> authorizedAsset = pageResult.getList();
            List<String> collect = authorizedAsset.stream().map(AuthorizedAsset::getAssetId).collect(Collectors.toList());
            authorizedAssetIds.addAll(collect);
            pageNo++;
        }
        log.info("返回的authorizedAssetIds是[{}]", authorizedAssetIds);
        return authorizedAssetIds;
    }

    public void getAssetByName(String assetName, AssetDTO assetDTO, List<AssetDTO> list, List<String> authorizedAssetIds) {
        if (Objects.isNull(assetDTO)) {
            return;
        }
        if (!StringUtils.isEmpty(assetDTO.getAsset_name()) && assetDTO.getAsset_name().contains(assetName)) {
            if (authorizedAssetIds.contains(assetDTO.getAsset_id())) {
                list.add(assetDTO);
            }
        }
        if (CollectionUtils.isEmpty(assetDTO.getSubAssets())) {
            return;
        }
        for (int i = 0; i < assetDTO.getSubAssets().size(); i++) {
            getAssetByName(assetName, assetDTO.getSubAssets().get(i), list, authorizedAssetIds);
        }
    }

    @Override
    public List<AssetDTO> getAssetByName(String assetName, String userId) {
        // TODO 云端API暂时未能提供接口，目前从缓存获取
        List<String> authorizedAssetIds = listAuthorizedAssetIds(userId);
        List<AssetDTO> result = new ArrayList<>();
        getAssetByName(assetName, ASSET_CACHE.get("-1"), result, authorizedAssetIds);
        return result;
    }

    @Override
    public List<AssetDTO> getAssetByNameV2(String assetName, String userId) {
        List<AuthorizedAsset> authorizedAssets = listAuthorizedAssets(userId);
        if (CollectionUtils.isEmpty(authorizedAssets)) {
            return new ArrayList<>();
        }
        Set<String> authedAssets = authorizedAssets.stream().map(AuthorizedAsset::getAssetId).collect(Collectors.toSet());
        List<Asset> assets = assetAbility.selectAssets(String.join(",", authedAssets));
        return assets.stream()
                .filter(asset -> asset.getAsset_name().contains(assetName))
                .sorted(Comparator.comparingInt(Asset::getLevel))
                .map(asset -> AssetDTO.builder()
                        .asset_id(asset.getAsset_id())
                        .asset_name(asset.getAsset_name())
                        .parent_asset_id(asset.getParent_asset_id())
                        .asset_full_name(asset.getAsset_full_name())
                        .level(asset.getLevel())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<AssetDTO> getChildAssetListBy(String assetId) {
        List<AssetDTO> result = AssetConvertor.$.toAssetDTOList(getChildAssetsBy(assetId));
        setAssetChildInfo(result);
        return result;
    }

    @Override
    public AssetDTO getTreeBy(String assetId, String userId) {
        AssetDTO tree = ASSET_CACHE.get("-1");
        AssetDTO originalTree = findTree(assetId, Lists.newArrayList(tree));
        return filterAuthorizedTree(originalTree, userId);
    }

    @Override
    public AssetDTO getTreeByV2(String assetId, String userId) {
        List<AuthorizedAsset> authorizedAssets = listAuthorizedAssets(userId);
        return buildAuthedAssetTree(authorizedAssets, assetId, true);
    }

    @Override
    public AssetDTO getTreeWithoutDevice(String assetId, String userId) {
        List<AuthorizedAsset> authorizedAssets = listAuthorizedAssets(userId);
        return buildAuthedAssetTree(authorizedAssets, assetId, false);
    }

    private List<AuthorizedAsset> listAuthorizedAssets(String userId) {
        log.info("查询用户userId[{}]授权的资产IDs", userId);
        List<AuthorizedAsset> authorizedAssets = new ArrayList<>();

        boolean hasMore = true;
        int pageNo = PAGE_NO;
        while (hasMore) {
            PageResultWithTotal<AuthorizedAsset> pageResult = assetAbility.pageListAuthorizedAssets(userId, pageNo, PAGE_SIZE);
            hasMore = pageResult.getHasMore().booleanValue();
            List<AuthorizedAsset> authorizedAsset = pageResult.getList();
            authorizedAssets.addAll(authorizedAsset);
            pageNo++;
        }
        return authorizedAssets;
    }


    /**
     * 批量查询资产
     *
     * @param assetIds
     * @return
     */
    private List<Asset> listAsset(Set<String> assetIds) {
        if (CollectionUtils.isEmpty(assetIds)) {
            return new ArrayList<>();
        }
        return assetAbility.selectAssets(String.join(",", assetIds));

    }

    /**
     * 构建授权的资产树
     *
     * @param list
     * @return
     */
    private AssetDTO buildAuthedAssetTree(List<AuthorizedAsset> list, String targetAssetId, boolean deviceFlag) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<AssetDTO> assetList = list.stream().map(authedAsset -> AssetDTO.builder()
                .asset_id(authedAsset.getAssetId())
                .parent_asset_id(authedAsset.getParentAssetId())
                .level(authedAsset.getLevel())
                .asset_name(authedAsset.getAssetName())
                .asset_full_name(authedAsset.getAssetName())
                .is_authorized(true)
                .subAssets(new ArrayList<>())
                .build()).collect(Collectors.toList());
        int maxLevel = maxOfAuthorizedAsset(list);
        // 便于设置资产下设备数量
        Map<String, AssetDTO> assetMap = assetList.stream().collect(Collectors.toMap(
                AssetDTO::getAsset_id, Function.identity()
        ));
        if (!"-1".equals(targetAssetId) && !assetMap.containsKey(targetAssetId)) {
            // TODO errorProcessor后续优化
            try {
                Asset asset = assetAbility.selectAsset(targetAssetId);
                AssetDTO assetDTO = AssetDTO.builder()
                        .asset_id(asset.getAsset_id())
                        .parent_asset_id(asset.getParent_asset_id())
                        .level(asset.getLevel())
                        .asset_name(asset.getAsset_name())
                        .asset_full_name(asset.getAsset_full_name())
                        .is_authorized(false)
                        .subAssets(new ArrayList<>())
                        .build();
                assetMap.put(targetAssetId, assetDTO);
                assetList.add(assetDTO);
            } catch (ConnectorException e) {
                if (e.getMessage().contains("1106")) {
                    return null;
                }
            }
        }
        // 从底层向上补全资产树
        Map<Integer, List<AssetDTO>> levelMap = assetList.stream().collect(Collectors.groupingBy(AssetDTO::getLevel)); // TODO 是否包含-1
        int targetLevel = "-1".equals(targetAssetId) ? 0 : assetMap.get(targetAssetId).getLevel();
        for (int i = maxLevel; i > targetLevel; i--) {
            List<AssetDTO> assetDTOS = levelMap.get(i);
            if (CollectionUtils.isEmpty(assetDTOS)) {
                continue;
            }
            Set<String> pids = assetDTOS.stream()
                    .map(AssetDTO::getParent_asset_id)
                    .filter(pAssetId -> !assetMap.containsKey(pAssetId))
                    .collect(Collectors.toSet());
            List<Asset> pAssetList = listAsset(pids);
            if (!CollectionUtils.isEmpty(pAssetList)) {
                levelMap.compute(
                        i - 1,
                        (level, assetDTOList) -> {
                            if (Objects.isNull(assetDTOList)) {
                                assetDTOList = new ArrayList<>();
                            }
                            assetDTOList.addAll(pAssetList.stream().map(
                                    asset -> AssetDTO.builder()
                                            .asset_id(asset.getAsset_id())
                                            .parent_asset_id(asset.getParent_asset_id())
                                            .level(asset.getLevel())
                                            .asset_name(asset.getAsset_name())
                                            .asset_full_name(asset.getAsset_name())
                                            .is_authorized(false)
                                            .subAssets(new ArrayList<>())
                                            .build()
                            ).collect(Collectors.toList()));
                            return assetDTOList;
                        }
                );
            }
        }

        // 自底向上构建资产树
        Map<String, AssetDTO> treeMap = levelMap.values().stream().flatMap(Collection::stream).collect(Collectors.toMap(
                AssetDTO::getAsset_id, Function.identity()
        ));
        for (int i = maxLevel; i > targetLevel; i--) {
            List<AssetDTO> curLevelAssets = levelMap.get(i);
            if (CollectionUtils.isEmpty(curLevelAssets)) {
                continue;
            }
            for (AssetDTO curLevelAsset : curLevelAssets) {
                AssetDTO pAssetDTO = treeMap.get(curLevelAsset.getParent_asset_id());
                List<AssetDTO> subAssets = pAssetDTO.getSubAssets();
                if (Objects.isNull(subAssets)) {
                    subAssets = new ArrayList<>();
                    pAssetDTO.setSubAssets(subAssets);
                }
                subAssets.add(curLevelAsset);
                pAssetDTO.setChild_asset_count(pAssetDTO.getChild_asset_count() + 1);
            }
        }

        // 自底向上构建资产树设备信息
        AssetDTO targetAsset;
        if ("-1".equals(targetAssetId)) {
            targetAsset =
                    AssetDTO.builder().asset_id(targetAssetId).subAssets(levelMap.get(0)).child_asset_count(levelMap.get(0).size()).build();
        } else {
            targetAsset = treeMap.get(targetAssetId);
        }
        if (deviceFlag) {
            Set<String> targetAuthedAssets = new HashSet<>();
            authedAssetIds(targetAsset, assetMap.keySet(), targetAuthedAssets);

            // 查询授权的资产下的设备信息
            targetAuthedAssets.parallelStream().forEach(
                    assetId -> {
                        List<String> deviceIds = getChildDeviceIdsBy(assetId);
                        assetMap.get(assetId).setChild_device_count(CollectionUtils.isEmpty(deviceIds) ? 0 : deviceIds.size());
                    }
            );
        }
        return targetAsset;
    }

    private int maxOfAuthorizedAsset(List<AuthorizedAsset> list) {
        int level = 0;
        for (AuthorizedAsset authorizedAsset : list) {
            if (authorizedAsset.getLevel() != null && authorizedAsset.getLevel().intValue() > level) {
                level = authorizedAsset.getLevel();
            }
        }
        return level;
    }

    /**
     * 查询指定资产树下的授权资产Id集合
     *
     * @param curAsset
     * @param totalAuthedAssets
     * @param curAuthedAssets
     */
    private void authedAssetIds(AssetDTO curAsset, Set<String> totalAuthedAssets, Set<String> curAuthedAssets) {
        if (totalAuthedAssets.contains(curAsset.getAsset_id())) {
            curAuthedAssets.add(curAsset.getAsset_id());
        }

        List<AssetDTO> subAssets = curAsset.getSubAssets();
        if (!CollectionUtils.isEmpty(subAssets)) {
            for (AssetDTO subAsset : subAssets) {
                authedAssetIds(subAsset, totalAuthedAssets, curAuthedAssets);
            }
        }
    }


    /**
     * 根据用户过滤资产树
     *
     * @param originalTree 原资产树对象
     * @param userId       用户ID
     * @return
     */
    private AssetDTO filterAuthorizedTree(AssetDTO originalTree, String userId) {
        List<String> authorizedAssetIds = listAuthorizedAssetIds(userId);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AssetDTO assetDTO = removeNonAuthorizeNode(SimpleConvertUtil.convert(originalTree, AssetDTO.class), authorizedAssetIds);
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("take [{}] ms for removing non-authorized assets", totalTimeMillis);
        return assetDTO;
    }


    private AssetDTO findTree(String assetId, List<AssetDTO> list) {
        for (AssetDTO assetDTO : list) {
            if (assetDTO.getAsset_id().equals(assetId)) {
                return assetDTO;
            } else {
                AssetDTO child = findTree(assetId, assetDTO.getSubAssets());
                if (!StringUtils.isEmpty(child.getAsset_id())) {
                    return child;
                }
            }

        }
        return new AssetDTO();
    }

    private List<AssetDTO> getTree(String assetId) {
        List<AssetDTO> result = AssetConvertor.$.toAssetDTOList(getChildAssetsBy(assetId));
        setAssetChildInfo(result);
        for (AssetDTO assetDTO : result) {
            List<AssetDTO> treeBy = getTree(assetDTO.getAsset_id());
            assetDTO.setSubAssets(treeBy);
        }
        return result;
    }

    private void setAssetChildInfo(List<AssetDTO> list) {
        for (AssetDTO assetDTO : list) {
            int childAssetCount = getChildAssetsBy(assetDTO.getAsset_id()).size();
            int childDeviceCount = getChildDeviceIdsBy(assetDTO.getAsset_id()).size();
            assetDTO.setChild_asset_count(childAssetCount);
            assetDTO.setChild_device_count(childDeviceCount);
        }
    }

    private List<Asset> getChildAssetsBy(String assetId) {
        List<Asset> assets = new ArrayList<>();
        final String pageSize = "100";
        boolean hasNext = true;
        String lastRowKey = "";
        while (hasNext) {
            PageResult<Asset> childAssetsBy = assetAbility.selectChildAssets(assetId, lastRowKey, pageSize);
            hasNext = childAssetsBy.getHasNext();
            lastRowKey = childAssetsBy.getLastRowKey();
            assets.addAll(childAssetsBy.getList());
        }
        return assets;
    }

    @Override
    public List<DeviceDTO> getChildDeviceInfoBy(String assetId) {
        List<String> deviceIdList = getChildDeviceIdsBy(assetId);
        return deviceService.getDevicesBy(deviceIdList);
    }

    @Override
    public PageDataVO<DeviceDTO> getChildDeviceInfoPage(String userId, String assetId, Integer pageNo, Integer pageSize) {
        PageDataVO<DeviceDTO> vo = new PageDataVO<>();
        vo.setPage_no(pageNo);
        vo.setPage_size(pageSize);
        vo.setData(Lists.newArrayList());
        vo.setTotal(0);
        //判断授权
        List<String> authList = listAuthorizedAssetIds(userId);
        if (CollectionUtils.isEmpty(authList) || !authList.contains(assetId)) {
            return vo;
        }
        List<String> deviceIdList = getChildDeviceIdsBy(assetId);
        if (CollectionUtils.isEmpty(deviceIdList)) {
            return vo;
        }
        vo.setTotal(deviceIdList.size());
        int begin = (pageNo - 1) * pageSize < vo.getTotal() ? (pageNo - 1) * pageSize : vo.getTotal();
        int end = pageNo * pageSize < vo.getTotal() ? pageNo * pageSize : vo.getTotal();
        List<String> resDeviceIdList = deviceIdList.subList(begin, end);
        List<DeviceDTO> devicesBy = deviceService.getDevicesBy(resDeviceIdList);
        vo.setData(devicesBy);
        return vo;
    }

    List<String> getChildDeviceIdsBy(String assetId) {
        List<String> deviceIds = new ArrayList<>();
        final String pageSize = "100";
        boolean hasNext = true;
        String lastRowKey = "";
        while (hasNext) {
            PageResult<Asset> childDevicesBy = assetAbility.selectChildDevices(assetId, lastRowKey, pageSize);
            hasNext = childDevicesBy.getHasNext();
            lastRowKey = childDevicesBy.getLastRowKey();
            deviceIds.addAll(childDevicesBy.getList().stream().map(Asset::getDevice_id).collect(Collectors.toList()));
        }
        return deviceIds;
    }


    private AssetDTO removeNonAuthorizeNode(AssetDTO dto, Collection<String> fullCollection) {
        if (CollectionUtils.isEmpty(fullCollection)) {
            return new AssetDTO();
        }
        List<String> tempColl = new ArrayList<>();
        tempColl.addAll(fullCollection);
        if (checkAssetMarked(dto, tempColl)) {
            if (!CollectionUtils.isEmpty(tempColl)) {
                batchAddAssetToCache(tempColl);
                return removeNonAuthorizeNode(SimpleConvertUtil.convert(ASSET_CACHE.get("-1"), AssetDTO.class), fullCollection);
            }
            return dto;
        }
        return new AssetDTO();
    }

    private void batchAddAssetToCache(List<String> tempColl) {
        List<Asset> assets = new ArrayList<>();
        int start = 0;
        int end = start + 20;
        String assetIds;
        while (start <= tempColl.size()) {
            if (end > tempColl.size()) {
                end = tempColl.size();
            }
            assetIds = StringUtils.join(tempColl.subList(start, end), ",");
            PageResult<Asset> pageResult = assetAbility.selectAssets(assetIds, "", "", 20);
            if (!CollectionUtils.isEmpty(pageResult.getList())) {
                assets.addAll(pageResult.getList());
            }
            start = end + 1;
            end = start + 20;
        }
        start = 0;
        end = assets.size();
        while (assets.size() > 0) {
            for (int i = assets.size() - 1; i >= 0; i--) {
                Asset asset = assets.get(i);
                AssetAddRequest request = new AssetAddRequest();
                request.setName(asset.getAsset_name());
                request.setParent_asset_id(asset.getParent_asset_id());
                if (addAssetToCache(asset.getAsset_id(), request, true)) {
                    assets.remove(i);
                }
            }
            if (start++ >= end) {
                break;
            }
        }
    }

    /**
     * 判断资产是否在权限集合内
     *
     * @param dto
     * @param fullCollection
     * @return
     */
    private boolean checkAssetMarked(AssetDTO dto, Collection<String> fullCollection) {
        //设立一个标志，用于判断其子节点下是否包含了有权限的节点
        boolean bx = false;
        int len = 0;
        List<AssetDTO> subAssets = dto.getSubAssets();
        if (!CollectionUtils.isEmpty(subAssets)) {
            bx = checkSubAssetMarked(subAssets, fullCollection);
            len = subAssets.size();
        }
        //将最新的数量设置进去
        dto.setChild_asset_count(len);
        boolean ax = fullCollection.contains(dto.getAsset_id());
        if (ax) {
            fullCollection.remove(dto.getAsset_id());
        }
        //子节点不包含且自身也不在权限集合内，则返回false
        return bx || ax;
    }

    /**
     * 判断自己点集合是否在权限集合内
     *
     * @param subAssets
     * @param fullCollection
     * @return
     */
    private boolean checkSubAssetMarked(List<AssetDTO> subAssets, Collection<String> fullCollection) {
        for (int i = subAssets.size() - 1; i >= 0; i--) {
            AssetDTO subAsset = subAssets.get(i);
            //节点不属于权限内，则移除
            if (!checkAssetMarked(subAsset, fullCollection)) {
                subAssets.remove(i);
            }
        }
        return subAssets.size() > 0;
    }

    /**
     * refresh asset tree
     */
    @Override
    public void refreshTree() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ASSET_CACHE.get("-1");
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("take [{}] ms for refreshing asset tress", totalTimeMillis);
    }

    @Override
    public List<AssetDTO> getTreeFast(String assetId, String userId) {
        AssetDTO assetDTO = buildAuthedAssetTree(listAuthorizedAssets(userId), assetId, true);
        return assetDTO.getSubAssets();
    }

    @Override
    public List<AssetDTO> getTreeByUser(String userId) {
        List<AuthorizedAsset> authorizedAssets = listAuthorizedAssets(userId);
        if (CollectionUtils.isEmpty(authorizedAssets)) {
            return new ArrayList<>();
        }
        AssetDTO assetDTO = buildAuthedAssetTree(authorizedAssets, "-1", false);
        return assetDTO != null ? assetDTO.getSubAssets() : new ArrayList<>();
    }

    @Override
    public Boolean authAssetToUser(String userId, List<String> assetIds) {
        List<String> authIds = listAuthorizedAssetIds(userId);
        Map<String, String> authMap = new HashMap<>();
        Map<String, String> grantMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(authIds)) {
            authMap = authIds.stream().collect(Collectors.toMap(e -> e, e -> e));
        }
        if (!CollectionUtils.isEmpty(assetIds)) {
            grantMap = assetIds.stream().collect(Collectors.toMap(e -> e, e -> e));
        }
        List<String> grantIds = new ArrayList<>();
        List<String> cancelIds = new ArrayList<>();
        //取出本次被取消的权限
        for (String assetId : authMap.keySet()) {
            if (!grantMap.containsKey(assetId)) {
                cancelIds.add(assetId);
            }
        }
        //取出已经被授权的，不重复授权
        for (String assetId : grantMap.keySet()) {
            if (!authMap.containsKey(assetId)) {
                grantIds.add(assetId);
            }
        }
        boolean opres = true;
        if (!CollectionUtils.isEmpty(cancelIds)) {
            opres = opres && batchAssetsUnAuthorizedToUser(userId, cancelIds, false);

        }
        if (!CollectionUtils.isEmpty(grantIds)) {
            opres = opres && batchAssetsAuthorizedToUser(userId, grantIds, false);
        }
        return opres;
    }


    @Override
    public boolean grantAllAssetByAdmin(String adminUserId, String userId) {
        List<String> adminAuthIds = listAuthorizedAssetIds(adminUserId);
        List<String> authorizedAssetIds = listAuthorizedAssetIds(userId);
        Map<String, String> authMap = new HashMap();
        if (!CollectionUtils.isEmpty(authorizedAssetIds)) {
            authMap = authorizedAssetIds.stream().collect(Collectors.toMap(e -> e, e -> e));
        }
        List<String> needGrantIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(authorizedAssetIds)) {
            for (String adminAuthId : adminAuthIds) {
                if (!authMap.containsKey(adminAuthId)) {
                    needGrantIds.add(adminAuthId);
                }
            }
        }
        if (!CollectionUtils.isEmpty(needGrantIds)) {
           return  batchAssetsAuthorizedToUser(userId, needGrantIds, false);
        }
        return false;
    }



    @Override
    public Boolean grantAllAsset(String adminUserId) {
        List<String> notAuthIds = findNotAuthAssetIdsByUser(adminUserId);
        if (!CollectionUtils.isEmpty(notAuthIds)) {
            return batchAssetsAuthorizedToUser(adminUserId,notAuthIds,false);
        }
        return true;
    }

    private List<String> findNotAuthAssetIdsByUser(String userId) {
        List<String> authorizedAssetIds = listAuthorizedAssetIds(userId);
        Map<String, String> authMap = new HashMap();
        if (!CollectionUtils.isEmpty(authorizedAssetIds)) {
            authMap = authorizedAssetIds.stream().collect(Collectors.toMap(e -> e, e -> e));
        }

        return findNotAutAssetIds("-1", authMap, 0);
    }

    private List<String> findNotAutAssetIds(String assetId, Map<String, String> authMap, int level) {
        List<String> needGrantIds = new ArrayList<>();
        if (level > 4) {
            return needGrantIds;
        }
        List<Asset> assetList = getChildAssetsBy(assetId);
        while (!CollectionUtils.isEmpty(assetList)) {
            for (Asset asset : assetList) {
                if (!authMap.containsKey(asset.getAsset_id())) {
                    needGrantIds.add(asset.getAsset_id());
                }
                needGrantIds.addAll(findNotAutAssetIds(asset.getAsset_id(), authMap, level+1));
            }
        }
        return needGrantIds;
    }


    private boolean batchAssetsUnAuthorizedToUser(String userId, List<String> cancelIds, boolean authorized_children) {
        return PageHelper.doListBySize(assetAuthSize, cancelIds, (ids) ->
                assetAbility.batchAssetsUnAuthorizedToUser(userId, new AssetAuthBatchToUser(userId, String.join(",", ids), authorized_children))
        );
    }

    private boolean batchAssetsAuthorizedToUser(String userId, List<String> grantIds, boolean authorized_children) {
        return PageHelper.doListBySize(assetAuthSize, grantIds, (ids) ->
                assetAbility.batchAssetsUnAuthorizedToUser(userId, new AssetAuthBatchToUser(userId, String.join(",", ids), authorized_children))
        );
    }
}
