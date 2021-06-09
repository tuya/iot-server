package com.tuya.iot.suite.service.idaas.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tuya.iot.suite.core.util.LazyRef;
import com.tuya.iot.suite.core.util.Tuple2;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.idaas.PermissionTemplateService;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;
import com.tuya.iot.suite.service.util.PermTemplateUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/03
 */
@Service
public class PermissionTemplateServiceImpl implements PermissionTemplateService {

    private final String defaultLang = Locale.CHINESE.getLanguage();

    /**
     * lang => roleType => permTrees
     * */
    private LoadingCache<String, Map<String, List<PermissionNodeDTO>>> permTreesMapCache = CacheBuilder
            .newBuilder()
            .build(new CacheLoader<String, Map<String, List<PermissionNodeDTO>>>() {
                @Override
                public Map<String, List<PermissionNodeDTO>> load(String lang) {
                    return Stream.of(RoleTypeEnum.values()).map(roleTypeEnum ->{
                        List<PermissionNodeDTO> trees;
                        try {
                             trees =
                                    PermTemplateUtil.loadTrees("classpath:template/permissions_" + lang + ".json", node ->
                                            node.getAuthRoleTypes().contains(roleTypeEnum.name()));
                        }catch (Exception e){
                            if(e instanceof FileNotFoundException){
                                trees =
                                        PermTemplateUtil.loadTrees("classpath:template/permissions_" + defaultLang + ".json", node ->
                                                node.getAuthRoleTypes().contains(roleTypeEnum.name()));
                            }else{
                                throw e;
                            }
                        }
                            return new Tuple2<>(roleTypeEnum.name(), trees);
                    }).collect(Collectors.toMap(it -> it.first(), it -> it.second()));
                }
            });
    /**
     * lang => roleType => permFlattenList
     */
    private LoadingCache<String, Map<String, List<PermissionNodeDTO>>> permFlattenListMapCache = CacheBuilder.newBuilder()
            .build(new CacheLoader<String, Map<String, List<PermissionNodeDTO>>>() {
                @Override
                public Map<String, List<PermissionNodeDTO>> load(String lang) {
                    return Stream.of(RoleTypeEnum.values()).map(roleTypeEnum -> {
                        List<PermissionNodeDTO> perms;
                        try {
                            perms = PermTemplateUtil.loadAsFlattenList("classpath:template/permissions_" + lang + ".json",
                                    node -> node.getAuthRoleTypes().contains(roleTypeEnum.name()));
                        }catch (Exception e){
                            if(e instanceof FileNotFoundException){
                                perms = PermTemplateUtil.loadAsFlattenList("classpath:template/permissions_" + defaultLang + ".json",
                                        node -> node.getAuthRoleTypes().contains(roleTypeEnum.name()));
                            }else{
                                throw e;
                            }
                        }
                        return new Tuple2<>(roleTypeEnum.name(), perms);
                    }).collect(Collectors.toMap(it -> it.first(), it -> it.second()));
                }
            });

    @Override
    public Set<String> getAuthorizablePermissions() {
        return getPermFlattenListMap(defaultLang).get(RoleTypeEnum.admin.name())
                .stream().filter(it -> it.getAuthorizable()).map(it -> it.getPermissionCode())
                .collect(Collectors.toSet());
    }


    @Override
    public List<PermissionNodeDTO> getTemplatePermissionTrees(String roleTypeOrRoleCode, String lang) {
        String roleType = RoleTypeEnum.fromRoleCode(roleTypeOrRoleCode).name();
        Map<String, List<PermissionNodeDTO>> permTreesMap = getPermTreesMap(lang);
        return permTreesMap.get(roleType);
    }

    @SneakyThrows
    private Map<String, List<PermissionNodeDTO>> getPermTreesMap(String lang) {
        Map<String, List<PermissionNodeDTO>> permTreesMap = permTreesMapCache.get(lang);
        if (permTreesMap == null) {
            permTreesMap = permTreesMapCache.getIfPresent(defaultLang);
        }
        return permTreesMap;
    }

    @Override
    public List<PermissionNodeDTO> getTemplatePermissionFlattenList(String roleTypeOrRoleCode, String lang) {
        String roleType = RoleTypeEnum.fromRoleCode(roleTypeOrRoleCode).name();
        Map<String, List<PermissionNodeDTO>> permFlattenListMap = getPermFlattenListMap(lang);
        return permFlattenListMap.get(roleType);
    }
    @SneakyThrows
    private Map<String, List<PermissionNodeDTO>> getPermFlattenListMap(String lang) {
        Map<String, List<PermissionNodeDTO>> permFlattenListMap = permFlattenListMapCache.get(lang);
        if (permFlattenListMap == null) {
            permFlattenListMap = permFlattenListMapCache.getIfPresent(defaultLang);
        }
        return permFlattenListMap;
    }


}
