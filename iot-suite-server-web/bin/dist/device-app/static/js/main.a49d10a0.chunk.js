!function(e,t){"object"===typeof exports&&"object"===typeof module?module.exports=t():"function"===typeof define&&define.amd?define([],t):"object"===typeof exports?exports["device-app-main"]=t():e["device-app-main"]=t()}(window,(function(){return(window["webpackJsonp_device-app"]=window["webpackJsonp_device-app"]||[]).push([[0],{176:function(e,t,n){e.exports={wrapper:"wrapper_1OdrC",item:"item_fiAnv"}},230:function(e,t,n){e.exports={styled:"styled_3o6sd","ant-tooltip-inner":"ant-tooltip-inner_o_1GK"}},233:function(e,t,n){e.exports={"modal-content":"modal-content_1_5ys"}},236:function(e,t,n){e.exports={"tree-highlight-value":"tree-highlight-value_1qNBk"}},252:function(e,t,n){window.__POWERED_BY_QIANKUN__&&(n.p=window.__INJECTED_PUBLIC_PATH_BY_QIANKUN__)},256:function(e,t,n){},34:function(e,t,n){e.exports={background:"background_slzll",assetTreeWrap:"assetTreeWrap_2-jB0",tableContainerWrap:"tableContainerWrap_1az3U",tableCtrlWrap:"tableCtrlWrap_HhaVM",ctrlLabel:"ctrlLabel_2MgNX",editInputHint:"editInputHint_VTmLk",cycle:"cycle_18QvG",green:"green_20DIG",red:"red_1N1d5",textBlue:"textBlue_3T0i2",tableCell:"tableCell_j9K89",drawerTitle:"drawerTitle_2joZ5",assetTreeHeight:"assetTreeHeight_29DMl"}},467:function(e,t,n){},468:function(e,t,n){"use strict";n.r(t),n.d(t,"bootstrap",(function(){return Ze})),n.d(t,"setGlobalState",(function(){return qe})),n.d(t,"micState",(function(){return Re})),n.d(t,"mainHistory",(function(){return Fe})),n.d(t,"mount",(function(){return et})),n.d(t,"unmount",(function(){return nt}));var a=n(29),c=n(93),i=n.n(c),r=n(163),o=n(0),l=(n(252),n(28)),s=n.n(l),d=n(134),u=n(121),j=n(18),b=n(241),h=n.n(b),f=n(240),O=n.n(f),v=(n(256),n(20)),p=n(227),m=n(482),x=n(471),g=n(472),y=n(473),C=n(52),S=n(474),w=n(485),N=n(481),k=n(46),_=n(88),D=n(480),I=n(231),T=n(74),E=n(80),A=n(228),P=n.n(A),V=n(176),L=n.n(V),W=n(6),B=function(e){var t=e.children;return Object(W.jsx)("div",{className:L.a.item,children:t})},z=function(e){var t=e.children;return Object(W.jsx)("div",{className:L.a.wrapper,children:t})},M=function(e){var t=e.defaultValue,n=e.name;return Object(W.jsxs)(z,{children:[Object(W.jsx)(B,{children:n}),Object(W.jsx)(P.a,{src:t}),";"]})},U=n(478),H=function(e){var t=e.disabled,n=void 0!==t&&t,c=e.defaultValue,i=void 0===c?"":c,r=e.name,o=void 0===r?"":r,l=e.options,s=void 0===l?{}:l,d=e.onChange,u=void 0===d?function(e){}:d;return Object(W.jsxs)(z,{children:[Object(W.jsx)(B,{children:o}),Object(W.jsx)(U.a,Object(a.a)(Object(a.a)({},s),{},{defaultValue:i,disabled:n,onChange:function(e){var t=e.target.value;u(t)}}))]})},q=n(119),R=q.a.Option,F=function(e){var t=e.name,n=void 0===t?"":t,a=e.defaultValue,c=e.disabled,i=e.onChange,r=e.data;return Object(W.jsxs)(z,{children:[Object(W.jsx)(B,{children:n}),Object(W.jsx)(q.a,{style:{width:"100%"},defaultValue:a,disabled:c,onChange:i,children:r.map((function(e){return Object(W.jsx)(R,{value:e,children:e},e)}))})]})},K=n(477),Q=n(230),Y=n.n(Q),G=function(e){var t,n=e.disabled,a=e.max,c=e.min,i=e.unit,r=e.defaultValue,o=e.step,l=e.onChange,s=e.name,d=(t={},Object(_.a)(t,c,"".concat(c).concat(i)),Object(_.a)(t,a,"".concat(a).concat(i)),t);return Object(W.jsxs)(z,{children:[Object(W.jsx)("label",{children:s}),Object(W.jsx)("div",{className:Y.a.styled,children:Object(W.jsx)(K.a,{tooltipVisible:!0,getTooltipPopupContainer:function(e){return e},marks:d,defaultValue:r,disabled:n,step:o,max:a,min:c,onChange:l})})]})},J=n(483),X=n(156),Z=function(e){var t=e.name,n=void 0===t?"":t,a=e.disabled,c=void 0!==a&&a,i=e.defaultValue,r=void 0!==i&&i,o=e.onChange,l=void 0===o?function(e){}:o;return Object(W.jsx)(z,{children:Object(W.jsxs)(m.b,{direction:"vertical",children:[Object(W.jsx)("label",{children:n}),Object(W.jsx)(J.a,{checkedChildren:Object(W.jsx)(X.a,{}),unCheckedChildren:Object(W.jsx)(E.a,{}),defaultChecked:r,disabled:c,onChange:function(e){l(e)}})]})})},$=n(34),ee=n.n($),te=k.apiService.getDeviceInfoWithDP,ne=k.apiService.modifyDeviceDP,ae=function(e){var t=e.title,n=void 0===t?"":t,c=e.visible,i=e.onConfirm,r=e.deviceId,l=Object(N.a)(),s=l.t,d=l.i18n.language,u=Object(o.useState)([]),j=Object(v.a)(u,2),b=j[0],h=j[1],f=Object(o.useState)({}),O=Object(v.a)(f,2),p=O[0],y=O[1],S=Object(o.useState)({}),w=Object(v.a)(S,2),k=w[0],A=w[1],P=Object(o.useState)(!1),V=Object(v.a)(P,2),L=V[0],B=V[1];Object(o.useEffect)((function(){c&&r&&(B(!0),te(r).then((function(e){if(B(!1),e){var t=e.status;h(t);var n={};t.forEach((function(e){n[e.code]={code:e.code,value:e.value,editable:e.editable}})),A(n),y((function(e){return Object.assign({},e,n)}))}})).catch((function(){B(!1)})))}),[r,c]);var z=function(){A([]),y({}),h([])};return Object(W.jsx)(D.a,{title:Object(W.jsxs)(x.a,{justify:"space-between",align:"middle",wrap:!1,children:[Object(W.jsx)(g.a,{children:Object(W.jsx)("span",{className:ee.a.drawerTitle,children:n})}),Object(W.jsx)(g.a,{children:Object(W.jsx)("span",{onClick:function(){i(p),z()},children:Object(W.jsx)(E.a,{})})})]}),footer:Object(W.jsx)("div",{style:{float:"right"},children:Object(W.jsxs)(m.b,{align:"end",children:[Object(W.jsx)(C.a,{onClick:function(){i(p),z()},children:s("ctrlDP.cancel")}),Object(W.jsx)(C.a,{type:"primary",onClick:function(){!function(){var e=[];if(Object.keys(p).forEach((function(t){var n=p[t].value,a=k[t];(null===a||void 0===a?void 0:a.editable)&&(null===a||void 0===a?void 0:a.value)!==n&&null!=n&&e.push({code:t,value:n})})),e.length>0)return ne(r,e).then((function(){i(e),z()}));z()}()},children:s("ctrlDP.confirm")})]})}),onClose:function(){i(p),z()},destroyOnClose:!0,width:"376",placement:"right",closable:!1,visible:c,children:L?Object(W.jsx)(I.a,{spinning:L,children:Object(W.jsx)(T.a,{})}):b.length?b.map((function(e,t){var n={defaultValue:e.value,disabled:!e.editable,name:"zh-CN"===d&&e.name||e.code,code:e.code,onChange:function(t){y((function(n){return Object.assign({},n,Object(_.a)({},e.code,{code:e.code,value:t}))}))}};if("Integer"===e.type){var c={};try{c=JSON.parse(e.options)}catch(x){console.error(x)}var i=c,r=i.max,l=i.min,s=i.scale,u=i.step,j=i.unit,b=void 0===j?"":j,h=function(e){return e/Math.pow(10,s)};return Object(o.createElement)(G,Object(a.a)(Object(a.a)({},n),{},{key:"".concat(e.code).concat(t).concat(e.value),unit:b,max:h(r),min:h(l),step:h(u)}))}if("Boolean"===e.type)return Object(W.jsx)(Z,Object(a.a)({},n),"".concat(e.code).concat(t).concat(e.value));if("String"===e.type){var f={};try{var O=JSON.parse(e.options);isNaN(+O.maxlen)||(f.maxlength=+O.maxlen)}catch(x){console.error(x)}return Object(W.jsx)(H,Object(a.a)(Object(a.a)({},n),{},{options:f}),"".concat(e.code).concat(t).concat(e.value))}if("Enum"===e.type){var v=[];try{var p=JSON.parse(e.options);p.range&&(v=p.range)}catch(x){console.error(x)}return Object(W.jsx)(F,Object(a.a)(Object(a.a)({},n),{},{data:v}),"".concat(e.code).concat(t).concat(e.value))}var m={};try{m=JSON.parse(e.value)}catch(x){console.error(x)}return Object(W.jsx)(M,Object(a.a)(Object(a.a)({},n),{},{defaultValue:m}),"".concat(e.code).concat(t).concat(Math.random()))})):Object(W.jsx)(T.a,{})})},ce=n(475),ie=n(479),re=ce.a.useForm,oe={labelCol:{span:6},wrapperCol:{span:16}},le=function(e){var t=e.modalStatus,n=void 0!==t&&t,c=e.onConfirm,i=void 0===c?function(e){return Promise.resolve(!0)}:c,r=e.onCancel,l=void 0===r?function(){}:r,s=e.title,d=void 0===s?"":s,u=e.children,j=(e.showLoading,re()),b=Object(v.a)(j,1)[0],h=Object(o.useState)(!1),f=Object(v.a)(h,2),O=f[0],p=f[1];return Object(W.jsx)(ie.a,{destroyOnClose:!0,visible:n,title:d,confirmLoading:O,onCancel:l,onOk:function(){b.validateFields().then((function(e){p(!0),i(e).then((function(){p(!1),b.resetFields()}))})).catch((function(e){console.error("Validate Failed:",e)}))},children:Object(W.jsx)(ce.a,Object(a.a)(Object(a.a)({form:b,preserve:!1},oe),{},{children:u}))})},se=k.apiService.modifyDeviceInfo,de=ce.a.Item,ue=function(e){var t=e.modalStatus,n=void 0!==t&&t,a=e.onConfirm,c=void 0===a?function(){}:a,i=e.deviceId,r=e.name,o=Object(N.a)().t;return Object(W.jsx)(le,{title:o("editDevice.title"),modalStatus:n,onConfirm:function(e){var t=e.deviceName;return se(i,t).then((function(){return c(!0),!0})).catch((function(e){return console.error(e),!0}))},onCancel:function(){c(!1)},children:Object(W.jsx)(de,{initialValue:r,label:o("editDevice.label"),name:"deviceName",rules:[{required:!0},{max:20,message:o("editDevice.errorMax")}],children:Object(W.jsx)(U.a,{})})})},je=n(233),be=n.n(je),he=function(e,t,n){var a=k.apiService.removeDeviceById;ie.a.confirm({title:t("removeDevice.title"),content:Object(W.jsx)("span",{className:be.a["modal-content"],children:t("removeDevice.content")}),okText:t("removeDevice.confirm"),cancelText:t("removeDevice.cancel"),onOk:function(){return a(e).then((function(){n&&n()}))}})},fe=n(234),Oe=n.n(fe),ve=n(484),pe=n(235),me=n.n(pe),xe=k.apiService.getProjectInfo;function ge(e){var t=document.createElement("a"),n=function(e){for(var t=e.split(";base64,"),n=t[0].split(":")[1],a=window.atob(t[1]),c=a.length,i=new Uint8Array(c),r=0;r<c;++r)i[r]=a.charCodeAt(r);return new Blob([i],{type:n})}(e.toDataURL("image/png"));t.download=(new Date).getTime()+".png",t.href=URL.createObjectURL(n),t.click()}var ye=function(){var e=Object(N.a)().t,t=Object(o.useState)(!1),n=Object(v.a)(t,2),a=n[0],c=n[1],i=Object(o.useState)(""),r=Object(v.a)(i,2),l=r[0],s=r[1],d=Object(o.useState)(""),u=Object(v.a)(d,2),j=u[0],b=u[1];return Object(o.useEffect)((function(){xe().then((function(e){var t=e.project_name,n=e.project_code;t&&s(t),n&&b(n)}))}),[]),Object(W.jsxs)(W.Fragment,{children:[Object(W.jsx)(C.a,{onClick:function(){c(!0)},type:"primary",icon:Object(W.jsx)(ve.a,{}),children:e("addDevice.btn")}),Object(W.jsxs)(ie.a,{width:600,destroyOnClose:!0,visible:a,title:e("addDevice.title"),onCancel:function(){c(!1)},cancelText:e("addDevice.close"),okText:e("addDevice.downloadQrCode"),onOk:function(){ge(document.getElementById("qrcode"))},getContainer:!1,children:[Object(W.jsxs)(x.a,{children:[Object(W.jsxs)(g.a,{span:14,children:[Object(W.jsx)(x.a,{justify:"center",children:Object(W.jsxs)(g.a,{className:"qrTitle",children:[e("addDevice.label"),": ",l]})}),Object(W.jsx)(x.a,{justify:"center",children:Object(W.jsx)(g.a,{children:Object(W.jsx)(me.a,{id:"qrcode",includeMargin:!0,size:200,value:"https://tuyasmart.applink.smart321.com/cloud/projects/".concat(j)})})}),Object(W.jsx)(x.a,{justify:"center",children:Object(W.jsx)(g.a,{className:"qrHint",children:e("addDevice.hint")})})]}),Object(W.jsx)(g.a,{span:10,children:Object(W.jsx)("img",{style:{width:259,height:233,marginLeft:-40},alt:"",src:"/device-app/qrcode-scan.png",className:"jsx-3947350688"})})]}),Object(W.jsx)(Oe.a,{id:"3947350688",children:[".qrTitle.jsx-3947350688{color:#262626;font-weight:bold;font-size:16px;}",".qrHint.jsx-3947350688{color:#595959;font-size:14px;text-align:center;}"]})]})]})},Ce=function(){return"zh-CN"===Object(N.a)().i18n.language?Object(W.jsx)(ye,{}):Object(W.jsx)("a",{href:"https://github.com/tuya/tuya-android-iot-app-sdk-sample",children:"Add Device with Tuya IoT App Sample"})},Se=n(62),we=n(35),Ne=n(249),ke=n(162),_e=n(476),De=n(238),Ie=n.n(De),Te=n(236),Ee=n.n(Te),Ae=function e(t,n){var c=[],i=[];return t.length?(n.forEach((function(n){var r=Object(a.a)({},n),o=r.title.indexOf(t);if(o>-1){i.push(n.key);var l=r.title.substr(0,o),s=r.title.substr(o+t.length);r.title=Object(W.jsxs)("span",{children:[l,Object(W.jsx)("span",{className:Ee.a["tree-highlight-value"],children:t}),s]}),r.children||c.push(r)}var d=i.length;if(r.children){var u=e(t,r.children),j=Object(v.a)(u,2),b=j[0],h=j[1];r.children=b,i.push.apply(i,Object(Se.a)(h))}i.length>d?(c.push(r),-1===o&&i.push(r.key)):o>-1&&r.children&&c.push(r)})),[c,i]):[n,i]},Pe=function e(t){var n=arguments.length>1&&void 0!==arguments[1]&&arguments[1],a=[];if(n){var c=t[0];a.push.apply(a,[c.key].concat(Object(Se.a)(e(c.children?c.children:[]))))}else t.length&&t.forEach((function(t){a.push.apply(a,[t.key].concat(Object(Se.a)(e(t.children?t.children:[]))))}));return a},Ve=function e(t){var n=[];return t.forEach((function(t){var a={key:t.asset_id,title:t.asset_name,checkable:!1,disabled:!1===t.is_authorized,selectable:!0,isLeaf:!0};(null===t||void 0===t?void 0:t.subAssets.length)&&(a.isLeaf=!1,a.children=e(t.subAssets)),n.push(a)})),n},Le=function e(t,n,a){var c=[];if(!Array.isArray(t)||!t.length)return c;for(var i=0;i<t.length;i++){var r=t[i];if(r.key===n){c.push.apply(c,[r.key].concat(Object(Se.a)(e(r.children,n,!0))));break}if(a)r.isLeaf?c.push(r.key):c.push.apply(c,Object(Se.a)(e(r.children,n,!0)));else{var o=e(r.children,n,!1);o.length?c.push.apply(c,[r.key].concat(Object(Se.a)(o))):c.push.apply(c,Object(Se.a)(o))}}return[].concat(c)},We=function(e){var t=e.loading,n=void 0!==t&&t,c=e.title,i=e.checkable,r=void 0!==i&&i,l=e.defaultSelectedValue,s=void 0===l?[]:l,d=e.defaultCheckedValue,u=void 0===d?[]:d,j=e.defaultExpandValue,b=void 0===j?[]:j,h=e.dataList,f=void 0===h?[]:h,O=e.onSelect,p=(e.onCheck,e.treeWrapProps),m=void 0===p?{}:p,x=(Object(Ne.a)(e,["loading","title","checkable","defaultSelectedValue","defaultCheckedValue","defaultExpandValue","dataList","onSelect","onCheck","treeWrapProps"]),Object(N.a)().t),g=Object(o.useState)(!0),y=Object(v.a)(g,2),C=y[0],S=y[1],w=Object(o.useState)(s),k=Object(v.a)(w,2),_=k[0],D=k[1],E=Object(o.useState)(f),A=Object(v.a)(E,2),P=A[0],V=A[1],L=Object(o.useState)(s),B=Object(v.a)(L,2),z=B[0],M=B[1];Object(o.useEffect)((function(){if(b.length)D(b),M(s);else if(f.length){var e=Pe(f,!0);D(e)}V(f)}),[f,b]);return Object(W.jsxs)(I.a,{spinning:n,children:[Object(W.jsx)(_e.a.Title,{level:4,children:c}),Object(W.jsx)(U.a,{allowClear:!0,maxLength:20,style:{marginBottom:15},placeholder:x("assetTree.searchPlaceholder"),onChange:function(e){var t=e.target.value,n=Ae(t,f),a=Object(v.a)(n,2),c=a[0],i=a[1];if(t.length)D(i);else if(z.length)D(Le(c,z[0],!1));else{var r=Pe(f,!0);D(r)}V(c),S(!0)},suffix:Object(W.jsx)(ke.a,{})}),!n&&P.length?Object(W.jsx)("div",Object(a.a)(Object(a.a)({},m),{},{id:"debugTree",children:Object(W.jsx)(Ie.a,{checkable:r,onExpand:function(e){S(!1),D(e)},autoExpandParent:C,expandedKeys:_,selectedKeys:z,defaultCheckedKeys:u,onSelect:function(e){if(e.length){var t=e[e.length-1];M([t]),O(t)}},onCheck:function(){},treeData:P})})):Object(W.jsx)(T.a,{})]})},Be=(n(239),function(e){var t=Object(N.a)().t,n=Object(we.e)(),c=Object(we.d)(),i=new URLSearchParams(Object(we.e)().search),r=Object(o.useState)(!1),l=Object(v.a)(r,2),s=l[0],d=l[1],u=Object(o.useState)([]),j=Object(v.a)(u,2),b=j[0],h=j[1],f=Object(o.useState)([]),O=Object(v.a)(f,2),p=O[0],m=O[1],x=Object(o.useState)([]),g=Object(v.a)(x,2),y=g[0],C=g[1];Object(o.useEffect)((function(){d(!0),k.apiService.getEntireTree().then((function(t){var a=Ve(t),r=i.get("assetId");e.autoHoldAssetId&&r?(m([r]),C(Object(Se.a)(Le(a,r,!1))),e.onSelect&&e.onSelect(r)):e.autoSelectFirst&&a.length&&(m([a[0].key]),C(Object(Se.a)(Le(a,a[0].key,!1))),c.push({pathname:n.pathname,search:"?assetId=".concat(a[0].key)}),e.onSelect&&e.onSelect(a[0].key)),h(a),d(!1)})).catch((function(){d(!1)}))}),[]);return Object(W.jsx)(We,Object(a.a)(Object(a.a)({title:t("assetTree.title"),loading:s,dataList:b,defaultSelectedValue:p,defaultExpandValue:y},e),{},{onSelect:function(t){c.push({pathname:n.pathname,search:"?assetId=".concat(t)}),e.onSelect(t)}}))}),ze=k.apiService.getDevicesInfoByAssetId,Me=function(){var e=Object(N.a)(),t=e.t,n=(e.i18n,Object(o.useState)([])),a=Object(v.a)(n,2),c=a[0],i=a[1],r=Object(o.useState)(1),l=Object(v.a)(r,2),s=l[0],d=l[1],u=Object(o.useState)(20),j=Object(v.a)(u,2),b=j[0],h=j[1],f=Object(o.useState)(0),O=Object(v.a)(f,2),k=O[0],_=O[1],D=Object(o.useState)(!1),I=Object(v.a)(D,2),T=I[0],E=I[1],A=Object(o.useState)(!1),P=Object(v.a)(A,2),V=P[0],L=P[1],B=Object(o.useState)(""),z=Object(v.a)(B,2),M=z[0],U=z[1],H=Object(o.useState)(""),q=Object(v.a)(H,2),R=q[0],F=q[1],K=function(){var e=arguments.length>0&&void 0!==arguments[0]&&arguments[0],t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"",n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"";L(e),U(t),F(n)},Q=Object(o.useState)(!1),Y=Object(v.a)(Q,2),G=Y[0],J=Y[1],X=Object(o.useState)(""),Z=Object(v.a)(X,2),$=Z[0],te=Z[1],ne=Object(o.useState)(""),ce=Object(v.a)(ne,2),ie=ce[0],re=ce[1],oe=function(){var e=arguments.length>0&&void 0!==arguments[0]&&arguments[0],t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"",n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"";J(e),te(t),re(n)},le=Object(o.useState)(""),se=Object(v.a)(le,2),de=se[0],je=se[1],be=Object(o.useState)(0),fe=Object(v.a)(be,2),Oe=fe[0],ve=fe[1],pe=function(){ve((function(e){return e+1}))};Object(o.useEffect)((function(){!function(e,t,n){e&&(E(!0),ze(e,t,n).then((function(e){E(!1),i((null===e||void 0===e?void 0:e.data)||[]),_((null===e||void 0===e?void 0:e.total)||0)})).catch((function(){E(!1),i([]),_(0)})))}(de,s,b)}),[Oe,de,s]);var me=[{title:t("table.column.name"),dataIndex:"name",width:"20%",className:ee.a.tableCell},{title:t("table.column.id"),dataIndex:"id",className:ee.a.tableCell},{title:t("table.column.online"),dataIndex:"online",render:function(e,n){return n.online?Object(W.jsxs)(m.b,{children:[Object(W.jsx)("span",{className:"".concat(ee.a.cycle," ").concat(ee.a.green)}),Object(W.jsx)("span",{className:ee.a.tableCell,children:t("table.row.online")})]}):Object(W.jsxs)(m.b,{children:[Object(W.jsx)("span",{className:"".concat(ee.a.cycle," ").concat(ee.a.red)}),Object(W.jsx)("span",{className:ee.a.tableCell,children:t("table.row.offline")})]})}},{title:t("table.column.activeTime"),dataIndex:"active_time",className:ee.a.tableCell,render:function(e,t){var n=p.unix(t.active_time).format("YYYY-MM-DD HH:mm:ss");return Object(W.jsx)("span",{className:ee.a.tableCell,children:n})}},{title:t("table.column.opt"),dataIndex:"opt",width:"20%",align:"center",render:function(e,n){return Object(W.jsxs)(m.b,{children:[Object(W.jsx)("div",{onClick:function(){oe(!0,n.name,n.id)},className:ee.a.textBlue,children:t("table.row.edit")}),Object(W.jsx)("div",{className:ee.a.textBlue,onClick:function(){K(!0,n.name,n.id)},children:t("table.row.ctrl")}),Object(W.jsx)("div",{className:ee.a.textBlue,onClick:function(){he(n.id,t,(function(){pe()}))},children:t("table.row.remove")})]})}}];return Object(W.jsxs)(W.Fragment,{children:[Object(W.jsx)("div",{className:ee.a.background,children:Object(W.jsxs)(x.a,{className:ee.a.ctrlWrapper,children:[Object(W.jsx)(g.a,{span:4,className:ee.a.assetTreeWrap,children:Object(W.jsx)(Be,{treeWrapProps:{className:ee.a.assetTreeHeight,style:{overflow:"auto"}},autoHoldAssetId:!0,autoSelectFirst:!0,title:t("assetTree.title"),onSelect:function(e){e&&(je(e),d(1))}})}),Object(W.jsx)(g.a,{children:Object(W.jsx)(y.a,{type:"vertical",style:{height:"100%"}})}),Object(W.jsxs)(g.a,{span:19,className:ee.a.tableContainerWrap,children:[Object(W.jsx)(x.a,{justify:"end",className:ee.a.tableCtrlWrap,children:Object(W.jsxs)(m.b,{children:[Object(W.jsx)(C.a,{type:"primary",icon:Object(W.jsx)(w.a,{}),onClick:pe,children:t("refresh")}),Object(W.jsx)(Ce,{})]})}),Object(W.jsx)(S.a,{loading:T,columns:me,rowKey:function(e){return e.id},dataSource:c,pagination:{current:s,defaultPageSize:b,pageSize:b,total:k,onChange:function(e){d(e)},onShowSizeChange:function(e,t){h(t),d(1),pe()},showSizeChanger:!1}})]})]})}),Object(W.jsx)(ae,{visible:V,deviceId:R,title:M,onConfirm:function(){K()}}),Object(W.jsx)(ue,{modalStatus:G,deviceId:ie,name:$,onConfirm:function(e){oe(),e&&pe()}})]})},Ue=n(244),He=function(){var e=[],t=Object(u.d)(),n=t.language;"zh-CN"===n?t.changeLanguage(n):t.changeLanguage("en-US"),k.configMethod.initGlobalConfig({headers:{"Accept-Language":"zh-CN"===n?n:"en-US"},baseURL:"/api",onError:function(t){var n=t.code,a=t.msg,c=t.apiMethodName;if("1010"===n)return localStorage.removeItem("_USERNAME"),void Fe.push("/login");e.includes(c)||Ue.b.error(a)}})};var qe,Re,Fe,Ke=function(){He();var e="zh-CN"===Object(u.d)().language?O.a:h.a;return Object(W.jsx)(j.b,{locale:e,children:Object(W.jsx)(Me,{})})},Qe=(n(467),n(248)),Ye=n(245),Ge={translation:{title:"Device Management",refresh:"refresh",table:{column:{name:"Device name",id:"Device ID",online:"Online status",activeTime:"Activation time",opt:"Operations"},row:{online:"Online",offline:"Offline",edit:"Edit",ctrl:"Control",remove:"Remove"}},assetCascader:{title:"Asset Filter",placeholder:"Select an asset"},addDevice:{btn:"Add device",title:"Scan the QR code with WeChat App to start device paring",close:"Close",downloadQrCode:"Download QR Code",label:"Project Name",hint:"Scan the code with WeChat, add device to the asset under your project on the Mini Program"},ctrlDP:{cancel:"Cancel",confirm:"Confirm",error:"Need to fill in"},removeDevice:{title:"Are you sure you want to remove this device?",content:"After the device is removed, you will not be able to manage and control the device. ",confirm:"Sure to remove",cancel:"Cancel",error:"Failed to remove the device!"},editDevice:{title:"Edit Device",label:"Device name",error:"Failed to edit the name!",errorMax:"Device name support up to 20 characters"},assetTree:{title:"Asset View",searchPlaceholder:"Asset name"}}},Je={translation:{title:"\u8bbe\u5907\u7ba1\u7406",refresh:"\u5237\u65b0",table:{column:{name:"\u8bbe\u5907\u540d\u79f0",id:"\u8bbe\u5907ID",online:"\u5728\u7ebf\u72b6\u6001",activeTime:"\u6fc0\u6d3b\u65f6\u95f4",opt:"\u64cd\u4f5c"},row:{online:"\u5728\u7ebf",offline:"\u79bb\u7ebf",edit:"\u7f16\u8f91",ctrl:"\u63a7\u5236",remove:"\u79fb\u9664"}},assetCascader:{title:"\u8d44\u4ea7\u7b5b\u9009",placeholder:"\u8d44\u4ea7\u9009\u62e9"},addDevice:{btn:"\u6dfb\u52a0\u8bbe\u5907",title:"\u914d\u7f51\u5c0f\u7a0b\u5e8f\u4e8c\u7ef4\u7801",close:"\u5173\u95ed",downloadQrCode:"\u4e0b\u8f7d\u4e8c\u7ef4\u7801",label:"\u9879\u76ee\u540d",hint:"\u7528\u5fae\u4fe1\u626b\u7801\uff0c\u5728\u6d82\u9e26\u667a\u80fd\u914d\u7f51\u5c0f\u7a0b\u5e8f\u4e0a\u4e3a\u60a8\u7684\u9879\u76ee\u4e0b\u7684\u8d44\u4ea7\u6dfb\u52a0\u8bbe\u5907"},ctrlDP:{cancel:"\u53d6\u6d88",confirm:"\u786e\u8ba4",error:"\u9700\u8981\u586b\u5199"},removeDevice:{title:"\u786e\u8ba4\u8981\u79fb\u9664\u8be5\u8bbe\u5907\u5417?",content:"\u8bbe\u5907\u79fb\u9664\u540e\uff0c\u60a8\u5c06\u4e0d\u80fd\u7ba1\u7406\u3001\u63a7\u5236\u8be5\u8bbe\u5907\u3002",confirm:"\u786e\u5b9a\u79fb\u9664",cancel:"\u53d6\u6d88",error:"\u79fb\u9664\u8bbe\u5907\u5931\u8d25!"},editDevice:{title:"\u7f16\u8f91\u8bbe\u5907",label:"\u8bbe\u5907\u540d\u79f0",error:"\u7f16\u8f91\u5931\u8d25!",errorMax:"\u8bbe\u5907\u540d\u79f0\u6700\u591a\u8f93\u516520\u4e2a\u5b57\u7b26"},assetTree:{title:"\u8d44\u4ea7\u7b5b\u9009",searchPlaceholder:"\u8bf7\u8f93\u5165\u8d44\u4ea7\u540d\u79f0"}}};function Xe(e){var t=e.base,n=e.container;s.a.render(Object(W.jsx)(d.a,{basename:window.__POWERED_BY_QIANKUN__?t:"/",children:Object(W.jsx)(Ke,{})}),n?n.querySelector("#root"):document.querySelector("#root"))}function Ze(){return $e.apply(this,arguments)}function $e(){return($e=Object(r.a)(i.a.mark((function e(){return i.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function et(e){return tt.apply(this,arguments)}function tt(){return(tt=Object(r.a)(i.a.mark((function e(t){return i.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:Object(u.d)().changeLanguage(t.lang),Fe=t.mainHistory,Xe(t),t.onGlobalStateChange((function(e,t){Re=e}),!0),qe=function(e){t.setGlobalState(Object(a.a)(Object(a.a)({},Re),e))};case 5:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function nt(e){return at.apply(this,arguments)}function at(){return(at=Object(r.a)(i.a.mark((function e(t){var n;return i.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:n=t.container,s.a.unmountComponentAtNode(n?n.querySelector("#root"):document.querySelector("#root"));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}Qe.a.use(Ye.a).use(u.e).init({resources:{"en-US":Ge,"zh-CN":Je},fallbackLng:"zh-CN",interpolation:{escapeValue:!1}}),window.__POWERED_BY_QIANKUN__||Xe({})}},[[468,1,2]]])}));
//# sourceMappingURL=main.a49d10a0.chunk.js.map