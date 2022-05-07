!function(e,t){"object"===typeof exports&&"object"===typeof module?module.exports=t():"function"===typeof define&&define.amd?define([],t):"object"===typeof exports?exports["iot-suite-asset-main"]=t():e["iot-suite-asset-main"]=t()}(window,(function(){return(window["webpackJsonp_iot-suite-asset"]=window["webpackJsonp_iot-suite-asset"]||[]).push([[0],{148:function(e,t,n){e.exports={"modal-content":"modal-content_YfMtW"}},186:function(e,t,n){e.exports={ctrlLabel:"ctrlLabel_2qIzd"}},205:function(e,t,n){window.__POWERED_BY_QIANKUN__&&(n.p=window.__INJECTED_PUBLIC_PATH_BY_QIANKUN__)},209:function(e,t,n){},210:function(e,t,n){},326:function(e,t,n){"use strict";n.r(t),n.d(t,"setGlobalState",(function(){return je})),n.d(t,"micState",(function(){return me})),n.d(t,"mainHistory",(function(){return pe})),n.d(t,"bootstrap",(function(){return ge})),n.d(t,"mount",(function(){return Ae})),n.d(t,"unmount",(function(){return Se}));var a=n(81),r=n.n(a),s=n(135),c=n(32),o=n(0),i=(n(205),n(28)),l=n.n(i),u=n(144),d=n(104),b=(n(209),n(201)),h=n(177),f={translation:{title:"Asset Management",table:{column:{name:"Asset name",id:"Asset ID",childrenAssetCount:"Number of child assets",childrenDeviceCount:"Number of devices",opt:"Operation"},row:{edit:"Edit",remove:"Delete"}},search:{title:"Search for Asset",placeholder:"Enter asset name for a fuzzy search",all:"All"},addAsset:{btn:"Create asset",title:"Create asset",parentLabel:"Parent Asset",extra:"If there is no parent asset, the current asset will become a primary asset",name:"Asset name",error:{fetch:"Operation failed!",max:"Asset name support up to 20 characters",required:"Required"}},editAsset:{title:"Edit Asset",name:"Asset name",error:{fetch:"Operation failed!",max:"Asset name support up to 20 characters",required:"Required"}},removeAsset:{title:"Operation Confirm",content:"There is no device under the current asset and all sub-assets. Deleting the current asset will also delete all the related sub-assets. Are you sure you want to delete the asset? ",ok:"Confirm to delete",cancel:"Cancel",hint:"Note",errorContent:"There is a device under the current asset (or one of the sub-assets) that has not been removed, so the asset cannot be deleted",ok2:"Got it"},assetCascader:{title:"Asset Filter",placeholder:"Select an asset"}}},j={translation:{title:"\u8d44\u4ea7\u7ba1\u7406",table:{column:{name:"\u8d44\u4ea7\u540d\u79f0",id:"\u8d44\u4ea7ID",childrenAssetCount:"\u5b50\u8d44\u4ea7\u6570\u91cf",childrenDeviceCount:"\u8bbe\u5907\u6570\u91cf",opt:"\u64cd\u4f5c"},row:{edit:"\u7f16\u8f91",remove:"\u5220\u9664"}},search:{title:"\u8d44\u4ea7\u641c\u7d22",placeholder:"\u8f93\u5165\u8d44\u4ea7\u540d\u79f0\u6a21\u7cca\u641c\u7d22",all:"\u5168\u90e8"},addAsset:{btn:"\u521b\u5efa\u8d44\u4ea7",title:"\u521b\u5efa\u8d44\u4ea7",parentLabel:"\u7236\u7ea7\u8d44\u4ea7",extra:"\u82e5\u65e0\u7236\u7ea7\u8d44\u4ea7\uff0c\u5f53\u524d\u8d44\u4ea7\u5c06\u6210\u4e3a\u4e00\u7ea7\u8d44\u4ea7",name:"\u8d44\u4ea7\u540d\u79f0",error:{fetch:"\u64cd\u4f5c\u5931\u8d25!",max:"\u8d44\u4ea7\u540d\u79f0\u6700\u591a\u8f93\u516520\u4e2a\u5b57\u7b26",required:"\u5fc5\u586b\u9879"}},editAsset:{title:"\u7f16\u8f91\u8d44\u4ea7",name:"\u8d44\u4ea7\u540d\u79f0",error:{fetch:"\u64cd\u4f5c\u5931\u8d25!",max:"\u8d44\u4ea7\u540d\u79f0\u6700\u591a\u8f93\u516520\u4e2a\u5b57\u7b26",required:"\u5fc5\u586b\u9879"}},removeAsset:{title:"\u64cd\u4f5c\u786e\u8ba4",content:"\u5f53\u524d\u8d44\u4ea7\u53ca\u6240\u6709\u5b50\u8d44\u4ea7\u4e0b\u6ca1\u6709\u8bbe\u5907\uff0c\u5220\u9664\u5f53\u524d\u8d44\u4ea7\u4f1a\u540c\u65f6\u5220\u9664\u8d44\u4ea7\u4e0b\u7684\u6240\u6709\u5b50\u8d44\u4ea7\uff0c\u786e\u5b9a\u8981\u5220\u9664\u8d44\u4ea7\u5417\uff1f",ok:"\u786e\u8ba4\u5220\u9664",cancel:"\u53d6\u6d88",hint:"\u63d0\u793a",errorContent:"\u5f53\u524d\u8d44\u4ea7\uff08\u6216\u5b50\u8d44\u4ea7\uff09\u4e0b\u6709\u8bbe\u5907\u672a\u79fb\u9664\uff0c\u65e0\u6cd5\u5220\u9664\u8d44\u4ea7",ok2:"\u77e5\u9053\u4e86"},assetCascader:{title:"\u8d44\u4ea7\u7b5b\u9009",placeholder:"\u8d44\u4ea7\u9009\u62e9"}}};b.a.use(h.a).use(d.e).init({resources:{"en-US":f,"zh-CN":j},fallbackLng:"zh-CN",interpolation:{escapeValue:!1}});var m=n(18),p=n(190),O=n.n(p),v=n(189),g=n.n(v),x=(n(210),n(23)),A=n(337),C=n(329),S=n(330),_=n(332),w=n(336),y=n(45),N=n(333),I=n(39),k=n(193),E=n(338),L=n(334),q=n(11),D=N.a.useForm,T={labelCol:{span:6},wrapperCol:{span:16}},R=function(e){var t=e.modalStatus,n=void 0!==t&&t,a=e.onConfirm,r=void 0===a?function(e){return Promise.resolve(!0)}:a,s=e.onCancel,i=void 0===s?function(){}:s,l=e.title,u=void 0===l?"":l,d=e.children,b=(e.showLoading,D()),h=Object(x.a)(b,1)[0],f=Object(o.useState)(!1),j=Object(x.a)(f,2),m=j[0],p=j[1];return Object(q.jsx)(L.a,{destroyOnClose:!0,visible:n,title:u,confirmLoading:m,onCancel:i,onOk:function(){h.validateFields().then((function(e){p(!0),r(e).then((function(){p(!1),h.resetFields()}))})).catch((function(e){console.log("Validate Failed:",e)}))},children:Object(q.jsx)(N.a,Object(c.a)(Object(c.a)({form:h,preserve:!1},T),{},{children:d}))})},z=n(196),U=n(26),B=n(182),P=n(335),F=function(e){var t=e.defaultValue,n=e.loading,a=e.title,r=void 0===a?"":a,s=e.options,i=void 0===s?[]:s,l=e.onChange,u=Object(w.a)().t,d=Object(o.useState)([]),b=Object(x.a)(d,2),h=b[0],f=b[1],j={};t.length&&(j.defaultValue=t);var m=function(e,t){f(e),l&&l(e,t)};return Object(o.useEffect)((function(){f(t)}),[t]),r?Object(q.jsxs)(A.b,{children:[Object(q.jsx)("span",{style:{marginLeft:15},children:r}),Object(q.jsx)(B.a,{spinning:n,children:Object(q.jsx)(P.a,{value:h,style:{width:"100%"},placeholder:u("assetCascader.placeholder"),options:i,onChange:m,expandTrigger:"hover",changeOnSelect:!0,dropdownRender:function(e){return Object(q.jsx)(B.a,{spinning:n,children:e})},allowClear:!1})})]}):Object(q.jsx)(B.a,{spinning:n,children:Object(q.jsx)(P.a,Object(c.a)(Object(c.a)({},j),{},{style:{width:"100%"},placeholder:u("assetCascader.placeholder"),options:i,onChange:m,expandTrigger:"hover",changeOnSelect:!0,dropdownRender:function(e){return Object(q.jsx)(B.a,{spinning:n,children:e})},allowClear:!1}))})},H=y.apiService.getEntireTree,W=function e(t,n,a){if(!Array.isArray(t))return[];var r=[];return t.forEach((function(t){r.push({value:t.asset_id,label:t.asset_name,children:(null===t||void 0===t?void 0:t.subAssets.length)&&n<a?e(t.subAssets,n+1,a):void 0})})),r},M=function e(t,n){for(var a=[],r=0;r<t.length;r++){var s=t[r];if(s.value===n)return[s.value];if(s.children){var c=e(s.children,n);c.length&&a.push.apply(a,[s.value].concat(Object(z.a)(c)))}}return a},V=function(e){var t=Object(o.useState)([]),n=Object(x.a)(t,2),a=n[0],r=n[1],s=Object(o.useState)(!1),i=Object(x.a)(s,2),l=i[0],u=i[1],d=Object(o.useState)([]),b=Object(x.a)(d,2),h=b[0],f=b[1],j=Object(U.d)(),m=Object(U.e)();return Object(o.useEffect)((function(){u(!0),H().then((function(t){u(!1);var n=W(t,1,e.maxDeepth);r(n);var a=m.query.assetId;if(e.autoHoldAssetId&&a){var s=M(n,a);s.length&&(f(s),e.onChange&&e.onChange([a]))}else e.autoSelectFirst&&n.length&&(f([n[0].value]),j.push({pathname:m.pathname,query:{assetId:n[0].value}}),e.onChange&&e.onChange([n[0].value]))})).catch((function(){u(!1)}))}),[]),Object(q.jsx)(F,Object(c.a)(Object(c.a)({defaultValue:h,loading:l},e),{},{options:a,onChange:function(t){if(null===t||void 0===t?void 0:t.length){if(e.autoHoldAssetId){var n=t[t.length-1];j.push({pathname:m.pathname,query:{assetId:n}})}e.onChange&&e.onChange(t)}}}))},K=y.apiService.addAsset,Y=N.a.Item,G=function(e){var t=e.onConfirm,n=void 0===t?function(){}:t,a=Object(w.a)().t,r=Object(o.useState)(!1),s=Object(x.a)(r,2),c=s[0],i=s[1],l=function(){var e=arguments.length>0&&void 0!==arguments[0]&&arguments[0];i(e)};return Object(q.jsxs)(q.Fragment,{children:[Object(q.jsx)(I.a,{onClick:function(){l(!0)},type:"primary",icon:Object(q.jsx)(E.a,{}),children:a("addAsset.btn")}),Object(q.jsxs)(R,{title:a("addAsset.title"),modalStatus:c,onConfirm:function(e){var t=e.deviceName,a=e.fatherAssetId,r=Array.isArray(a)?a[a.length-1]:"";return K(t,r).then((function(){return l(),n(),!0})).catch((function(e){return console.error(e),!0}))},onCancel:function(){l()},children:[Object(q.jsx)(Y,{label:a("addAsset.parentLabel"),extra:a("addAsset.extra"),name:"fatherAssetId",children:Object(q.jsx)(V,{title:"",maxDeepth:4,autoSelectFirst:!1,autoHoldAssetId:!1})}),Object(q.jsx)(Y,{label:a("addAsset.name"),name:"deviceName",rules:[{required:!0,message:a("addAsset.error.required")},{max:20,message:a("addAsset.error.max")}],children:Object(q.jsx)(k.a,{})})]})]})},Q=y.apiService.editAsset,J=N.a.Item,X=function(e){var t=e.modalStatus,n=void 0!==t&&t,a=e.onConfirm,r=void 0===a?function(){}:a,s=e.assetId,c=e.name,o=Object(w.a)().t;return Object(q.jsx)(R,{title:o("editAsset.title"),modalStatus:n,onConfirm:function(e){var t=e.assetName;return Q(s,t).then((function(){return r(!0),!0})).catch((function(e){return console.error(e),!0}))},onCancel:function(){r(!1)},children:Object(q.jsx)(J,{initialValue:c,label:o("editAsset.name"),name:"assetName",rules:[{required:!0,message:o("editAsset.error.required")},{max:20,message:o("editAsset.error.max")}],children:Object(q.jsx)(k.a,{})})})},Z=n(143),$=n(148),ee=n.n($),te=function e(t){var n;if(t.child_device_count>0)return!0;if((null===(n=t.subAssets)||void 0===n?void 0:n.length)&&t.subAssets.filter((function(t){return e(t)})).length)return!0;return!1},ne=function(e,t){return new Promise((function(n){if(te(e))return function(e){L.a.info({title:e("removeAsset.hint"),content:Object(q.jsx)("span",{className:ee.a["modal-content"],children:e("removeAsset.errorContent")}),okText:e("removeAsset.ok2")})}(t),void n(!1);!function(e,t){L.a.confirm({icon:null,title:e("removeAsset.title"),content:Object(q.jsx)("span",{className:ee.a["modal-content"],children:e("removeAsset.content")}),okText:e("removeAsset.ok"),cancelText:e("removeAsset.cancel"),onOk:t})}(t,(function(){return Object(Z.removeAsset)(e.asset_id).then((function(){n(!0)}))}))}))},ae=n(331),re=n(186),se=n.n(re),ce=y.apiService.searchAssetByName,oe=function(e){var t=e.onSelect,n=Object(w.a)().t,a=Object(o.useState)(""),r=Object(x.a)(a,2),s=r[0],c=r[1],i=Object(o.useState)([]),l=Object(x.a)(i,2),u=l[0],d=l[1];return Object(o.useEffect)((function(){}),[]),Object(q.jsxs)(C.a,{align:"middle",children:[Object(q.jsx)(S.a,{children:Object(q.jsx)("label",{className:se.a.ctrlLabel,children:n("search.title")})}),Object(q.jsx)(S.a,{children:Object(q.jsx)(ae.a,{dropdownMatchSelectWidth:252,style:{width:300},options:u,value:s,onSelect:function(e,n){t(e),c(n.label)},onSearch:function(e){ce(e).then((function(e){var t=e.map((function(e){return{label:e.asset_name,value:e.asset_id}}));d(t)})),c(e)},onChange:function(e){0===e.length&&t("-1")},children:Object(q.jsx)(k.a.Search,{placeholder:n("search.placeholder")})})})]})},ie=n(66),le=n.n(ie),ue=y.apiService.getSubTree,de=function e(t){var n=[];return t.forEach((function(t){Array.isArray(t.subAssets)&&t.subAssets.length?n.push(Object(c.a)(Object(c.a)({},t),{},{children:e(t.subAssets)})):n.push(Object(c.a)({},t))})),n},be=function(){var e=Object(w.a)().t,t=Object(o.useState)([]),n=Object(x.a)(t,2),a=n[0],r=n[1],s=Object(o.useState)(1),c=Object(x.a)(s,2),i=c[0],l=c[1],u=Object(o.useState)(20),d=Object(x.a)(u,2),b=d[0],h=d[1],f=Object(o.useState)(0),j=Object(x.a)(f,2),m=j[0],p=j[1],O=Object(o.useState)(!1),v=Object(x.a)(O,2),g=v[0],y=v[1],N=Object(o.useState)("-1"),I=Object(x.a)(N,2),k=I[0],E=I[1],L=Object(o.useState)(!1),D=Object(x.a)(L,2),T=D[0],R=D[1],z=Object(o.useState)(""),U=Object(x.a)(z,2),B=U[0],P=U[1],F=Object(o.useState)(""),H=Object(x.a)(F,2),W=H[0],M=H[1],V=function(){var e=arguments.length>0&&void 0!==arguments[0]&&arguments[0],t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"",n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"";R(e),P(t),M(n)},K=Object(o.useState)(0),Y=Object(x.a)(K,2),Q=Y[0],J=Y[1],Z=function(){J((function(e){return e+1}))};Object(o.useEffect)((function(){!function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"-1";y(!0),ue(e).then((function(t){y(!1);var n=[];n="-1"!==e&&t.asset_id?[t]:t.subAssets,r(de(n)),p(n.length)})).catch((function(){y(!1),r([]),p(0)}))}(k)}),[Q,k]);var $=[{title:e("table.column.name"),dataIndex:"asset_name",width:"20%",className:le.a.tableCell},{key:"asset_id",title:e("table.column.id"),dataIndex:"asset_id",className:le.a.tableCell},{title:e("table.column.childrenAssetCount"),dataIndex:"child_asset_count",className:le.a.tableCell},{title:e("table.column.childrenDeviceCount"),dataIndex:"child_device_count",className:le.a.tableCell},{title:e("table.column.opt"),dataIndex:"opt",width:"20%",align:"center",render:function(t,n){return Object(q.jsxs)(A.b,{children:[Object(q.jsx)("span",{onClick:function(){V(!0,n.asset_name,n.asset_id)},className:le.a.textBlue,children:e("table.row.edit")}),Object(q.jsx)("span",{onClick:function(){return ne(n,e).then((function(e){e&&Z()}))},className:le.a.textRed,children:e("table.row.remove")})]})}}];return Object(q.jsxs)("div",{className:le.a.background,children:[Object(q.jsxs)(C.a,{justify:"space-between",className:le.a.ctrlWrapper,children:[Object(q.jsx)(S.a,{children:Object(q.jsx)(oe,{onSelect:function(e){E(e)}})}),Object(q.jsx)(S.a,{children:Object(q.jsx)(G,{onConfirm:function(){Z()}})})]}),Object(q.jsx)(_.a,{loading:g,columns:$,rowKey:function(e){return e.asset_id},dataSource:a,pagination:{current:i,defaultPageSize:b,pageSize:b,total:m,onChange:function(e){l(e)},onShowSizeChange:function(e,t){h(t)}}}),Object(q.jsx)(X,{isEdit:!0,modalStatus:T,name:B,assetId:W,onConfirm:function(e){V(),e&&Z()}})]})},he=n(195),fe=function(){var e=["getDevicesInfoByAssetId"],t=Object(d.d)(),n=t.language;"zh-CN"===n?t.changeLanguage(n):t.changeLanguage("en-US"),y.configMethod.initGlobalConfig({headers:{"Accept-Language":"zh-CN"===n?n:"en-US"},baseURL:"/api",onError:function(t){var n=t.code,a=t.msg,r=t.apiMethodName;if("1010"===n||"111"===n)return localStorage.removeItem("_USERNAME"),localStorage.removeItem("_UID"),localStorage.removeItem("_ROLE_TYPE"),void window.location.replace("/login");e.includes(r)||he.b.error(a)}})};var je,me,pe,Oe=function(e){fe();var t="zh-CN"===Object(d.d)().language?g.a:O.a;return Object(q.jsx)(m.a,{locale:t,children:Object(q.jsx)(be,{})})};function ve(e){var t=e.base,n=e.container;l.a.render(Object(q.jsx)(u.a,{basename:window.__POWERED_BY_QIANKUN__?t:"/",children:Object(q.jsx)(Oe,Object(c.a)({},e))}),n?n.querySelector("#root"):document.querySelector("#root"))}function ge(){return xe.apply(this,arguments)}function xe(){return(xe=Object(s.a)(r.a.mark((function e(){return r.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:console.log("\u8d44\u4ea7\u5fae\u5e94\u7528\u542f\u52a8");case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function Ae(e){return Ce.apply(this,arguments)}function Ce(){return(Ce=Object(s.a)(r.a.mark((function e(t){return r.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:console.log("\u6302\u8f7d\u8d44\u4ea7\u5fae\u5e94\u7528",t),Object(d.d)().changeLanguage(t.lang),pe=t.mainHistory,ve(t),t.onGlobalStateChange((function(e,t){console.log("\u8d44\u4ea7\u5fae\u5e94\u7528",e,t),me=e}),!0),je=function(e){t.setGlobalState(Object(c.a)(Object(c.a)({},me),e))};case 6:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function Se(e){return _e.apply(this,arguments)}function _e(){return(_e=Object(s.a)(r.a.mark((function e(t){var n;return r.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:n=t.container,l.a.unmountComponentAtNode(n?n.querySelector("#root"):document.querySelector("#root"));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}window.__POWERED_BY_QIANKUN__||ve({})},66:function(e,t,n){e.exports={background:"background_slzll",ctrlWrapper:"ctrlWrapper_3uAda",ctrlLabel:"ctrlLabel_2MgNX",editInputHint:"editInputHint_VTmLk",tableCell:"tableCell_j9K89",textBlue:"textBlue_3T0i2",textRed:"textRed_31INU"}}},[[326,1,2]]])}));