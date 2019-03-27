/**
 * Created by daihui on 2018/1/4.
 */
//创建cesium Viewer
viewer = new Cesium.Viewer('earthContainer', {
    animation: false, //是否创建动画小器件，左下角仪表
    baseLayerPicker: false, //是否显示图层选择器
    fullscreenButton: false, //是否显示全屏按钮
    geocoder: false, //是否显示geocoder小器件，右上角查询按钮
    homeButton: true, //是否显示Home按钮
    infoBox: false, //是否显示信息框
    sceneModePicker: false, //是否显示3D/2D选择器
    selectionIndicator: true, //是否显示选取指示器组件
    timeline: false, //是否显示时间轴
    navigationHelpButton: false, //是否显示右上角的帮助按钮
    scene3DOnly: true, //如果设置为true，则所有几何图形以3D模式绘制以节约GPU资源
    navigationInstructionsInitiallyVisible: false,
    showRenderLoopErrors: false,
    //加载自定义地图瓦片需要指定一个自定义图片服务器//URL 为瓦片数据服务器地址
    imageryProvider : new Cesium.WebMapTileServiceImageryProvider({
        // url: "http://t0.tianditu.com/vec_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=vec&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default&format=tiles",
        url: "http://t0.tianditu.gov.cn/vec_c/wmts?tk=a9252aa89556311507a0acc5370dbfec",
        layer: "tdtVecBasicLayer",
        style: "default",
        format: "image/jpeg",
        tileMatrixSetID: "GoogleMapsCompatible",
        show: false
    })
});
// 全球影像中文注记服务
viewer.imageryLayers.addImageryProvider(new Cesium.WebMapTileServiceImageryProvider({
    url: "http://t0.tianditu.com/cia_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=cia&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default.jpg",
    layer: "tdtAnnoLayer",
    style: "default",
    format: "image/jpeg",
    tileMatrixSetID: "GoogleMapsCompatible",
    show: false
}));
// 全球矢量中文注记服务
viewer.imageryLayers.addImageryProvider(new Cesium.WebMapTileServiceImageryProvider({
    url: "http://t0.tianditu.com/cva_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=cva&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default.jpg",
    layer: "tdtAnnoLayer",
    style: "default",
    format: "image/jpeg",
    tileMatrixSetID: "GoogleMapsCompatible"
}));
// 去掉entity的双击事件
viewer.screenSpaceEventHandler.setInputAction(function(){},Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK );
// 去掉版本信息
viewer._cesiumWidget._creditContainer.style.display="none";
// 更改Home相机默认的范围
Cesium.Camera.DEFAULT_VIEW_RECTANGLE=Cesium.Rectangle.fromDegrees(-180,-180,0,0);
// 取消双击
viewer.cesiumWidget.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);

scene = viewer.scene;
handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);




