/**
 * Created by daihui on 2018/1/4.
 */

// 获取高程
// var cartographic = viewer.scene.globe.ellipsoid.cartesianToCartographic (cartesian);
// //将弧度转为度的十进制度表示
// var longitudeString = Cesium.Math.toDegrees(cartographic.longitude);
// var latitudeString = Cesium.Math.toDegrees(cartographic.latitude);
// var height=viewer.scene.globe.getHeight(cartographic);




function drawPoint(news) {
    var point = {
        name: news.newsAddressName,
        id:news.newsId,
        position: Cesium.Cartesian3.fromDegrees(news.lgt, news.lat),
        point: { //点
            pixelSize: 10,
            color: Cesium.Color.RED,
            outlineColor: Cesium.Color.WHITE,
            outlineWidth: 2
        },
        label : { //文字标签
            text: news.newsAddressName+'('+news.newsCount+')',
            font : '14pt monospace',
            style: Cesium.LabelStyle.FILL_AND_OUTLINE,
            outlineWidth : 2,
            //垂直位置
            verticalOrigin : Cesium.VerticalOrigin.BUTTON,
            //中心位置
            pixelOffset : new Cesium.Cartesian2(0, 20)
        }
    };
    viewer.entities.add(point);
}


handler.setInputAction(function(click) {
    var pickedObject = scene.pick(click.position);
    // 选中某个模型
    if (Cesium.defined(pickedObject) && pickedObject.id) {
        $("#infoBoxDiv").empty();
        var newsAddressName = pickedObject.id.name;
        var newsTitleDomStart = "<ol style='padding-left:17px;color:#eee;'>";
        var newsTitleDomBody = "";
        var newsTitleDomEnd = "</ol>";
        $.getJSON('/getTitles', {newsAddressName: newsAddressName}, function (titleList) {
            $.each(titleList, function (index, newsTitle) {
                newsTitleDomBody += "<li style='list-style:decimal'><a onclick='clickShowDetail(this.innerText);' style='color: #eeeeee'>" + newsTitle + "</a></li>";
            });
            $("#infoBoxDiv").append("<h5 style='text-align: center'>"+newsAddressName+"新闻</h5>")
                .append("<div style='height: 100px;width: 193px;overflow: auto'>"+newsTitleDomStart + newsTitleDomBody + newsTitleDomEnd+"</div>");
        });
        $("#infoBoxDiv").show();

    }else {
        $("#infoBoxDiv").empty();
        $("#infoBoxDiv").hide();
    }
}, Cesium.ScreenSpaceEventType.LEFT_CLICK);


function clickShowDetail(newsTitle) {
    $.getJSON("/getNews",{newsTitle:newsTitle},function (news) {
        var newsContent = news.newsContent;
        $("#detail_title").text(news.newsTitle);
        $("#detail_time").html(news.newsDate);
        $("#detail_content").html(newsContent);
        //显示图表
        showChart(newsContent);
        showChart2(newsContent);
        showChart3(newsContent);
        analyzeEmotion(newsContent);
        showChart5(newsContent);
    });
}

$(function () {
    $.getJSON('/getAddress', function (newsList) {
        $.each(newsList, function (index, news) {drawPoint(news);});
    });
});


