var CONTEXT_PATH = "";
var AJAX = (function () {
    var get = function (url, data, callback) {
        request("get", url, data, callback);
    };
    var post = function (url, data, callback) {
        request("post", url, data, callback);
    };
    var request = function (method, url, data, callback) {
        if (typeof data == "function") {
            callback = data;
            data = {};
        }
        url = url + (url.indexOf("?") > 0 ? "&" : "?") + "timestamp=" + new Date().getTime();
        $.ajax({
            url: url,
            data: data,
            type: method,
            dataType: "json",
            error: function (r) {
                if (callback) {
                    callback(false, r.responseJSON);
                }
            },
            success: function (json) {
                if (callback) {
                    callback(true, json);
                }
            }
        });
    };
    return {
        get: get,
        post: post
    }
})();
var NAVIGATION = (function () {
    var history;
    var listen = function () {
        window.onhashchange = function () {
            hash();
        };
        hash();
    };
    var hash = function () {
        var url = window.location.hash.substring(1);
        if (url === "" || url === "/") {
            url = history;
        }
        load(url);
        $("aside a").removeClass("active");
        var menu = $("aside [href='#" + url + "']");
        menu.addClass("active");
        menu.parents("li").addClass("menu-open").find("a:first").addClass("active");
    }
    var load = function (url) {
        var full = url + (url.indexOf("?") > 0 ? "&" : "?") + "timestamp=" + new Date().getTime();
        $("#main_load").load(CONTEXT_PATH + full, function (r, s) {
            if (s === "success") {
                history = url;
            } else {
                window.location.hash = history;
            }
        });
    };
    var reload = function () {
        hash();
    }
    var init = function (url, contextPath) {
        history = url;
        CONTEXT_PATH = contextPath;
        listen();
    };
    var to = function (url) {
        window.location.hash = url;
    }
    return {
        init: init,
        to: to,
        reload: reload
    }
})();
// var STATISTICS = (function () {
//     var update = function () {
//         $.get("/statistics/today/", function (json) {
//             $(".workitem_today").html(json.workitem_today);
//             $(".workitem_all").html(json.workitem_all);
//             $(".workitem_overtime").html(json.workitem_overtime);
//             $(".workitem_following").html(json.workitem_following);
//             $(".todo_waiting").html(json.todo_waiting);
//             $.each($(".bidding_today"), function () {
//                 var key = $(this).attr("count_key");
//                 var count = json.bidding_today[key];
//                 $(this).html(count ? count : 0);
//             });
//             $.each($(".opportunity_today"), function () {
//                 var key = $(this).attr("count_key");
//                 var count = json.opportunity_today[key];
//                 $(this).html(count ? count : 0);
//             });
//             $(".channel").html(json.channel);
//         })
//     };
//     var auto = function (t) {
//         setInterval(function () {
//             update();
//         }, t * 1000);
//     };
//     var init = function (t) {
//         auto(t);
//         update();
//     };
//     return {
//         init: init,
//         update: update
//     }
// })();

function getUrlParams(key) {
    var url = window.location.hash.substring(1);
    var params = {};
    if (url.indexOf("?") === -1) {
        return key ? null : params;
    }
    paramstr = "&" + url.split("?")[1];
    var strs = paramstr.split("&");
    for (var i = 0; i < strs.length; i++) {
        if (strs[i] === "") {
            continue;
        }
        params[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
    }
    return key ? (params[key] === undefined ? null : params[key]) : params;
}

function setUrlParams(key, value) {
    var url = window.location.hash.substring(1);
    var prefix = url.split("?")[0];
    var params = getUrlParams();
    params[key] = value;
    var url = prefix + "?";
    $.each(params, function (k, v) {
        url = url + k + "=" + encodeURI(v) + "&";
    });
    url = url.substring(0, url.length - 1);
    window.location.hash = url;
}

var globalCache = {};
var GRID = (function () {
    var table, form, count, isInit, cacheData = {};
    var list = function (el, fields, rowClickCallback, callback, setFormCache, onRefreshed) {
        isInit = true;
        table = $(el);
        form = $(table.attr("form"));
        count = $(table.attr("count"));
        if (hasCache()) {
            if (setFormCache) {
                setFormCache(form, getCache())
            } else {
                form.find("[name='title']").val(getCache().title);
            }
        }
        form.submit(function () {
            reset();
            return false;
        });
        table.jsGrid({
            width: "100%",
            selecting: true,
            autoload: true,
            paging: true,
            pageIndex: hasCache() ? getCache().pageIndex : 1,
            pageSize: hasCache() ? getCache().pageSize : 20,
            pageLoading: true,
            rowClick: function (args) {
                if (rowClickCallback) {
                    rowClickCallback(args.item);
                }
            },
            fields: fields,
            controller: {
                loadData: function (filter) {
                    return loadData(filter, callback);
                }
            },
            pageButtonCount: 10,
            loadMessage: "?????????...",
            noDataContent: "????????????",
            pagerFormat: "{first} {prev} {pages} {next} {last}    ??? {pageCount} ??? / {itemCount} ???",
            pagePrevText: "?????????",
            pageNextText: "?????????",
            pageFirstText: "??????",
            pageLastText: "??????",
            pageNavigatorNextText: "...",
            pageNavigatorPrevText: "...",
            onRefreshed: onRefreshed,
        });
    };
    var hasCache = function () {
        return !!cacheData[window.location.hash];
    };
    var clearCache = function () {
        cacheData = {}
        globalCache = {}
    };
    var getCache = function () {
        return cacheData[window.location.hash];
    };
    var setCache = function (data) {
        cacheData[window.location.hash] = data;
    };
    var reset = function () {
        table.jsGrid("reset");
    };
    var reload = function () {
        table.jsGrid("loadData");
    };
    var loadData = function (filter, callback) {
        var url = form.attr("action") + "?" + form.serialize();
        var deferred = $.Deferred();
        $.ajax({
            url: url,
            method: form.attr("method"),
            data: {
                page: filter.pageIndex,
                pageSize: filter.pageSize
            },
            dataType: "json"
        }).done(function (response) {
            deferred.resolve(response);
            count.html(response.itemsCount);
            if (callback) {
                callback(form, response);
            } else {
                setCache({
                    pageIndex: response.pageIndex,
                    pageSize: response.pageSize,
                    title: form.find("[name='title']").val()
                });
            }
        });
        return deferred.promise();
    };
    return {
        list: list,
        reset: reset,
        reload: reload,
        hasCache: hasCache,
        setCache: setCache,
        clearCache: clearCache
    }
})();

function timediff(date, des) {
    var str = "";
    if (date.length !== 19 && date.length !== 21) {
        return date;
    }
    date = date.substring(0, 19).replace(/-/g, "/");
    var diff = new Date().getTime() - new Date(date).getTime();
    diff = diff / 1000;
    if (!des) {
        des = "???";
    }
    if (diff / 3600 < 1) {
        str = parseInt(diff / 60) + "??????" + des;
    } else if (diff / 86400 < 1) {
        str = parseInt(diff / 3600) + "??????" + des;
    } else {
        str = parseInt(diff / 86400) + "???" + des;
    }
    return str;
}

function timediff2(date1, date2) {
    var diff = new Date(date2).getTime() - new Date(date1).getTime();
    diff = diff / 1000;
    if (diff / 3600 < 1) {
        var a = parseInt(diff / 60);
        if (a === 0) {
            str = "??????";
        } else {
            str = parseInt(diff / 60) + "?????????";
        }
    } else if (diff / 86400 < 1) {
        str = parseInt(diff / 3600) + "?????????";
    } else {
        str = parseInt(diff / 86400) + "??????";
    }
    return str;
}

/**
 * ????????????????????????????????????????????????
 * @param {Object} arg1
 * @param {Object} arg2
 */
function accAdd(arg1, arg2) {
    var r1 = deal(arg1);
    var r2 = deal(arg2);
    var m = Math.pow(10, Math.max(r1, r2))
    return (arg1 * m + arg2 * m) / m
}

/**
 * ????????????????????????????????????????????????
 * @param {Object} arg1
 * @param {Object} arg2
 */
function accMul(arg1, arg2) {
    var m = 0;
    m += deal(arg1);
    m += deal(arg2);
    var r1 = Number(arg1.toString().replace(".", ""));
    var r2 = Number(arg2.toString().replace(".", ""));
    return (r1 * r2) / Math.pow(10, m)
}

/**
 * ????????????????????????????????????????????????
 * @param {Object} arg1
 * @param {Object} arg2
 */
function accDiv(arg1, arg2) {
    var t1 = deal(arg1);
    var t2 = deal(arg2);
    var r1 = Number(arg1.toString().replace(".", ""))
    var r2 = Number(arg2.toString().replace(".", ""))
    return (r1 / r2) * Math.pow(10, t2 - t1);
}

/**
 * ??????????????????????????????
 */
function deal(arg) {
    var t = 0;
    try {
        t = arg.toString().split(".")[1].length
    } catch (e) {
    }
    return t;
}

function getCompany(tenderingCompany) {
    let company = ''
    if (tenderingCompany != null && tenderingCompany !== '') {
        company += '<p class="small font-italic" style="margin-bottom: 0"> ???????????????  ';
        company += tenderingCompany.tenderer;
        if (tenderingCompany.winNum != null && tenderingCompany.winNum !== '' && tenderingCompany.winNum > 0) {
            company += '<span style="margin-left:6px;color: #103178"> ????????? </span>'
        }
        if (tenderingCompany.importantCustomer) {
            company += '<span style="margin-left:6px;color: #103178"> ???????????? </span>'
        }
        if (tenderingCompany.longTermCustomer) {
            company += '<span style="margin-left:6px;color: #103178"> ???????????? </span>'
        }
        company += '</p> '
    }
    return company;
}

function fieldValueCheck(str) {
    if (str === null || str === undefined) {
        str = ''
    }
    return str;
}

function goBack() {
    window.history.back();
}

function jumpOneTenderInfo(that) {
    let id = $(that).attr("tenderInfoId")
    if (id === null || id === '' || id === undefined) {
        return
    }
    NAVIGATION.to('/tenderinfo/price/analysis/one/tender?id=' + id);
}

function jumpOneCompany(that) {
    let name = $(that).attr("companyName")
    if (name === null || name === '' || name === undefined) {
        return
    }
    NAVIGATION.to('/tenderinfo/price/analysis/one/company?oneCompany=' + name);
}

function print() {
    $("#monthTable").print({
        debug: false,//?????????true???????????????iframe???????????????iframe??????????????????????????????????????????????????????????????????false
        importCSS: true, //true??????????????????????????????css????????????true???????????????true????????????$("link[media=print]")?????????????????????$("link")??????css?????????
        printContainer: true,//???????????????????????????????????????????????????????????????????????????false?????????????????????CSS????????????
        operaSupport: true
    });
}

function exportExcel(type, tableID, fn, dl) {
    var elt = document.getElementById(tableID);
    var wb = XLSX.utils.table_to_book(elt, {sheet: "Sheet JS"});
    return dl ?
        XLSX.write(wb, {bookType: type, bookSST: true, type: 'base64'}) :
        XLSX.writeFile(wb, fn || ('tbbb-export.' + (type || 'xlsx')));
}

function biddingMoveOut(biddingSubId) {
    if (!confirm("???????????????")) {
        return;
    }
    $.get("/api/bidding/move/out/", {
        biddingSubId: biddingSubId,
    }, function (json) {
        if (json.code === "200") {
            Swal.fire({
                icon: 'success',
                title: '????????????',
                text: "????????????????????????",
                showCancelButton: true,
                confirmButtonText: "??????????????????",
                cancelButtonText: "??????"
            }).then((isConfirm) => {
                if (isConfirm.value) {
                    location.href = `/bidding/view/${json.biddingId}/`
                } else {
                    goBack()
                }
            })
        }
    });

}