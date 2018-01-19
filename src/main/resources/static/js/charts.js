// 词频统计
function statisticWord(result) {
    var myChart1 = echarts.init(document.getElementById('chart1'));
    var xData = [];
    var seriesData = [];
    $.each(result.substring(1, result.length - 1).split(','), function (i, v) {
        xData.push(v.split('=')[0]);
        seriesData.push(v.split('=')[1]);
    });
    var option = {
        title: {
            text: '词频统计'
        },
        tooltip: {},
        legend: {
            data: ['数量']
        },
        xAxis: {
            data: xData
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: seriesData
        }]
    };
    myChart1.setOption(option);
}


//关键字提取
function extractKeyword(result) {
    var dataArr = [];
    $.each(result, function (i, v) {
        var dataObj = {};
        dataObj.name = v;
        dataObj.value = 10;
        dataArr.push(dataObj);
    });
    var myChart2 = echarts.init(document.getElementById('chart2'));
    myChart2.setOption({
        series: [{
            type: 'wordCloud',
            shape: 'pentagon',
            gridSize: 2,
            sizeRange: [10, 40],
            rotationRange: [-90, 90],
            drawOutOfBound: true,
            textRotation: [0, 45, 90, -45],
            textPadding: 0,
            textStyle: {
                normal: {
                    color: function () {
                        return 'rgb('
                            + [Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)]
                                .join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            autoSize: {
                enable: true,
                minSize: 14
            },
            data: dataArr
        }]
    });
}


//命名实体识别
function extractEntity(result) {
    var viewNodeArr = [];//要展示的节点
    var relationNodeArr = [];//节点之间的关系
    $.each(result, function (i, v) {//要展示的节点
        var viewNodeObj = {};
        viewNodeObj.name = v.name;
        viewNodeObj.id = v.id;
        viewNodeObj.pid = v.pid;
        viewNodeArr.push(viewNodeObj);
    });

    $.each(result, function (ri, rv) {//节点之间连接
        $.each(viewNodeArr, function (vi, vv) {
            if (rv.id == vv.pid) {
                var relationNodeObj = {};
                relationNodeObj.source = rv.id;//起始节点，0表示第一个节点
                relationNodeObj.target = vi;//目标节点，1表示与索引为1的节点进行连接
                relationNodeArr.push(relationNodeObj);
            }
        });
    });
    var myChart3 = echarts.init(document.getElementById('chart3'));
    var webkitDep = {
        "type": "force",
        "nodes": viewNodeArr,
        "links": relationNodeArr
    };
    webkitDep.nodes.forEach(function (node) {
        node.itemStyle = {
            normal: {
                label: {
                    show: true,
                    textStyle: {
                        color: '#333'
                    }
                },
                nodeStyle: {
                    brushType: 'both',
                    borderColor: 'rgba(255,215,0,0.4)',
                    borderWidth: 1
                },
                linkStyle: {
                    type: 'curve'
                }
            }
        };
        if (node.pid == -1) {//第一层
            node.symbolSize = 100;
            node.itemStyle.normal.color = 'red';
        } else if (node.pid == 0) {//第二层
            node.symbolSize = 50;
            node.itemStyle.normal.color = 'blue';
        } else {//第三层
            node.symbolSize = 20;
            node.itemStyle.normal.color = 'green';
        }
        node.value = node.symbolSize;
        node.symbolSize /= 1.5;
        node.category = node.name;
    });

    option = {
        series: [{
            type: 'graph',
            layout: 'force',
            animation: false,
            label: {
                normal: {
                    show: true,
                    position: 'right'
                }
            },
            roam: true,
            lineStyle: {
                normal: {
                    color: 'source',
                    curveness: 0.3
                }
            },
            draggable: true,
            data: webkitDep.nodes.map(function (node, idx) {
                node.id = idx;
                return node;
            }),
            force: {
                edgeLength: 105,//连线的长度
                repulsion: 100  //子节点之间的间距
            },
            edges: webkitDep.links
        }]
    };
    myChart3.setOption(option);
}


//情感分析
function analyzeEmotion(content) {
    var myChart4 = echarts.init(document.getElementById('chart4'));
    option = {
        // legend: {
        //     orient: 'vertical',
        //     x: 'left',
        //     data:['正','负','喜','怒','哀','乐']
        // },
        // tooltip: {
        //     trigger: 'item',
        //     formatter: "{a} <br/>{b}: {c} ({d}%)"
        // },
        // series: [
        //     {
        //         name:'分析评价',
        //         type:'pie',
        //         selectedMode: 'single',
        //         radius: [0, '30%'],
        //
        //         label: {
        //             normal: {
        //                 position: 'inner'
        //             }
        //         },
        //         labelLine: {
        //             normal: {
        //                 show: false
        //             }
        //         },
        //         data:[
        //             {value:482, name:'正', selected:true},
        //             {value:369, name:'负'}
        //         ]
        //     },
        //     {
        //         name:'情感分析',
        //         type:'pie',
        //         radius: ['40%', '55%'],
        //
        //         data:[
        //             {value:335, name:'喜'},
        //             {value:234, name:'怒'},
        //             {value:135, name:'哀'},
        //             {value:147, name:'乐'}
        //         ]
        //     }
        // ]
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        series: [{
            name: '分析指标',
            type: 'gauge',
            axisLabel: {
                formatter: function (value) {
                    switch (value + "") {
                        case "10":
                            return "正";
                        case "20":
                            return "";
                        case "30":
                            return "";
                        case "40":
                            return "";
                        case "50":
                            return "中性";
                        case "60":
                            return "";
                        case "70":
                            return "";
                        case "80":
                            return "";
                        case "90":
                            return "负";
                        case "100":
                            return "";
                        default:
                            return "";
                    }
                },
                textStyle: {fontSize: 15}
            },
            detail: {
                formatter: function (value) {
                    return "";
                }
            },
            data: [{value: 50}]
        }]
    };
    myChart4.setOption(option);
}

