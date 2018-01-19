/**
 * Created by daihui on 2018/1/4.
 */
$(function () {
    var item = $(".item:first").children();
    $("#detail_title").text($(item[0]).children()[0].innerText);
    $("#detail_time").html($(item[0]).children()[1].innerHTML);
    var content = $(item[1]).children()[0].innerHTML;
    $("#detail_content").html(content);
    //显示图表
    showChart(content);
    showChart2(content);
    showChart3(content);
    analyzeEmotion(content);
    showChart5(content);
});

/**
 * 显示详情
 * @param obj
 */
function showDetail(obj) {
    $("#detail_title").text(obj.innerText);
    $("#detail_time").html($(obj).siblings("span")[0].innerHTML);
    var content = $($(obj).parent().siblings("div")[0]).children()[0].innerHTML
    $("#detail_content").html(content);
    //显示图表
    showChart(content);
    showChart2(content);
    showChart3(content);
    analyzeEmotion(content);
    showChart5(content);
}

/**
 * 根据详情显示图表--词频统计
 * @param content 内容
 */
function showChart(content){
    $.ajax({
        url : '/chart',
        method : 'post',
        data : {content:content},
        success : function (result) {
            statisticWord(result);
        },
        error : function(data){
            alert("出错了。");
        }
    });
}

/**
 * 根据详情显示图表--关键字提取
 * @param content 内容
 */
function showChart2(content){
    $.ajax({
        url : '/chart2',
        method : 'post',
        data : {content:content},
        success : function (result) {
            extractKeyword(result);
        },
        error : function(data){
            alert("出错了。");
        }
    });
}

/**
 * 根据详情显示图表--命名实体抽取
 * @param content 内容
 */
function showChart3(content){
    $.ajax({
        url : '/chart3',
        method : 'post',
        data : {content:content},
        success : function (result) {
            extractEntity(result);
        },
        error : function(data){
            alert("出错了。");
        }
    });
}


/**
 * 根据详情显示图表--内容摘要
 * @param content 内容
 */
function showChart5(content){
    $.ajax({
        url : '/chart5',
        method : 'post',
        data : {content:content},
        success : function (result) {
            $('#chart5').text(result);
            $('#chart5').css({color:"#000", "font-weight":"600","text-indent": "30px","padding":"10px"});
        },
        error : function(data){
            alert("出错了。");
        }
    });
}