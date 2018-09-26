/*
*
** 这里开始开发自定义插件
** 自执行函数：
    ** // 写法一
    (function(){})()

    //写法二
    (function(){}())
** js里面()括号就是将代码结构变成表达式，被包在()里面的变成了表达式之后，则就会立即执行，js中将一段代码变成表达式有很多种方式，比如：
    ** void function(){...}();
    // 或者
    !function foo(){...}();
    // 或者
    +function foot(){...}();
*
*/

; // JavaScript 弱语法的特点,如果前面刚好有个函数没有以";"结尾,那么可能会有语法错误
(function(undefined){
    /**"use strict"**/
    var _global;
    // 定义一些默认参数
    var _DefaultParam = {
        environment: "development",
        // environment: "product",
        projectName: "cfChicken8",
        versionNO: "1536897675562",
        versionExceptArr: ["eoulu.ico","bootstrap","swiper-3.4.1.min.css","eouluCustom.css","reset.css","css/libs","css/extends","echarts","jquery","msgbox","ajaxfileupload.js","html2canvas.js","fullcalendar.min.js","jsPdf.debug.js","underscore-min.js","ProvinceandCity.js","dispatchScript-a9a7544bda.min.js","js/libs","plugins/"],
        chartColorArr: [
              '#DE3656','#D6395B','#C93F64','#C0436C','#B64873','#A35283','#9D5588','#885F98','#7468A6','#6C6BA6','#5F72B6','#5A74B9','#5077BE','#4B7BC5','#467DC9','#4081CE','#3A83D3','#3183DA','#2A8BDF','#248EE3','#1E91E8','#1993EC','#1396F0','#0F98F4','#0B9AF7','#069CFA','#019FFF'
            ],
        allDepartArr: ["软件部","财务部","人事部","商务部","销售部","市场部","服务部","物流部","硬件部","应用部","标准服务部","研发部","厦门办事处"],
        canDispatchPageArr_href: ["ApplicationGallery", "SoftwareDocument", "Transport", "GetOrderRoute", "GetOrderByPageOne", "GetOrderByPageTwo", "PaymentRequest", "ServiceReport", "AssessmentStatistics", "Hardware", "GetHardwareRoute", "StandardProduct", "Customer", "GetCustomerInfo2", "AllLab", "Lab", "Reimburse", "Schedule", "ScheduleRoute"],
        notEouluCopy_href: ["OriginFactory","OriginFactorySearch","SoftwareImplementation","Tasking","Keysight","Price","PriceRoute","Transport","GetOrderRoute","GetOrderByPageOne", "GetOrderByPageTwo", "NonStandard","Inventory","OriginalQuotation","Supplier","AssessmentStatistics","StaffInfo","TrainingRecords","Admin","ServiceReport","SoftwareDocument","StandardProduct","Lab","Reimburse","WorkReport","SoftwareProject","BiddingDocument","PackingList","PaymentRequest","ApplicationGallery","Hardware","GetHardwareRoute","Customer","GetCustomerInfo2","AllLab"],
        showNavArr_href: ["Schedule","SoftwareProduct","QuotationSystem","Transport", "GetOrderRoute", "GetOrderByPageOne", "GetOrderByPageTwo", "PackingList","Invoice","Equipment","OriginFactory","Inventory","Price","Requirement","Inventory","MachineDetails","SalesStatistics","LeaveApplication","DocumentUpload","QuotationSystem","StockPurchasing","Insurance","Proposal","PackingList","HotProduct","Commodity","Quality","QuantityWeight","TestReport","Fumigation","Origin","Shipment","Receiving","Acceptance","Customer","MachineDetails","Hardware","AfterSale","Schedule","RoutineVisit","SoftwareDocument","SoftwareProject","SoftwareProduct","CustomerInquiry","SoftwareImplementation","StaffInfo","LeaveApplication","SoftwareImplementation","Keysight","NonStandard","OriginalQuotation","Supplier","AssessmentStatistics","ServiceReport","StandardProduct","SalesQuotationSystem","Lab","Reimburse","WorkReport","BiddingDocument","PaymentRequest","ApplicationGallery","AllLab"],
        pageAllConfig: {
            "Schedule": {
                provinceANDcityRefreshFlag: false
            }
        },
        depart2PageObj: {
                "物流部": {
                    // "物流统计": "Transport?ActualDelivery=no&column=DateOfSign&condition=All",
                    "物流统计": "Transport",
                    "物流统计#": "GetOrderRoute",
                    "物流统计##": "GetOrderByPageOne",
                    "物流统计###": "GetOrderByPageTwo",
                    // "库存采购": "StockPurchasing?ActualDelivery=no&column=DateOfSign&condition=All",
                    "库存采购": "StockPurchasing",
                    "库存采购#": "PriceRoute",
                    "库存采购##": "GetOrderByPageOneInPrice",
                    "库存页面": "Inventory",
                    "FORMFACTOR": "OriginFactory",
                    "FORMFACTOR#": "OriginFactorySearch",
                    "箱单制作": "PackingList",
                    "进出口运输指令": "Insurance",
                    "国内运输指令": "TransportDirective",
                    "运输保险": "Proposal",
                    "供应商管理": "Supplier",
                    "商品管理": "Commodity"
                },
                "商务部": {
                    // "合同统计": "Price?ActualDelivery=no&column=DateOfSign&condition=All",
                    "合同统计": "Price",
                    "需求统计": "Requirement",
                    "Keysight": "Keysight",
                    "销售统计": "SalesStatistics",
                    "热销产品": "HotProduct",
                    "报价系统": "QuotationSystem",
                    "发票制作": "Invoice",
                    "质量证明": "Quality",
                    "数量重量": "QuantityWeight",
                    "测试报告": "TestReport",
                    "熏蒸证明": "Fumigation",
                    "原产地证明": "Origin",
                    "验收报告": "Receiving",
                    "发货通知": "Shipment",
                    "FAT": "Acceptance",
                    "客户信息表": "Customer",
                    "客户信息表#": "GetCustomerInfo2",
                    "原厂报价单": "OriginalQuotation",
                    "工作汇报": "WorkReport",
                    "招标文件": "BiddingDocument"
                },
                "服务部": {
                    "机台统计": "MachineDetails",
                    "装机进展": "Hardware",
                    "装机进展#": "GetHardwareRoute",
                    "售后维修": "AfterSale",
                    "例行拜访": "RoutineVisit",
                    "标准产品": "StandardProduct",
                    "服务任务分配": "NonStandard",
                    "员工行程": "Schedule",
                    "员工行程#": "ScheduleRoute",
                    "服务完成报告": "ServiceReport",
                    "文档管理": "DocumentUpload",
                    "研发图库": "ApplicationGallery"
                },
                "软件部": {
                    "软件部文档": "SoftwareDocument",
                    "开发项目管理": "SoftwareProject",
                    "软件产品管理": "SoftwareProduct",
                    "客户询价记录": "CustomerInquiry",
                    "软件实施管理": "SoftwareImplementation"
                },
                "人事部": {
                    "员工信息": "StaffInfo",
                    "请假申请": "LeaveApplication",
                    "人事任务分配": "Tasking",
                    "考核明细": "AssessmentStatistics",
                    "报销申请": "Reimburse",
                    "付款申请": "PaymentRequest"
                },
                "销售部": {
                    "销售报价系统": "SalesQuotationSystem"
                },
                "实验室": {
                    "所有设备清单": "AllLab",
                    "设备清单": "Lab"
                }
            },
        // pageHrefImportFileMap 开始
        pageHrefImportFileMap: {
                // 研发图库页面
                "ApplicationGallery": {
                    "lastModify": "Fri Aug 31 2018 12:41:55 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1535690515000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "global_table_style.css": {
                            "referenceOrder": 1,
                            "filePath": "css/global/global_table_style.css",
                            "mergeToParentFile": "ApplicationGallery.css"
                        },
                        "ApplicationGallery.css": {
                            "referenceOrder": 2,
                            "filePath": "css/modules/serviced/ApplicationGallery.css",
                            "mergeToParentFile": "ApplicationGallery.css"
                        },
                        "add_update_section.css": {
                            "referenceOrder": 3,
                            "filePath": "css/global/add_update_section.css",
                            "mergeToParentFile": "ApplicationGallery.css"
                        },
                        "eoulu_ul_reset.css": {
                            "referenceOrder": 4,
                            "filePath": "css/global/eoulu_ul_reset.css",
                            "mergeToParentFile": "ApplicationGallery.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "ApplicationGallery.js": {
                            "referenceOrder": 1,
                            "filePath": "js/modules/serviced/ApplicationGallery.js",
                            "mergeToParentFile": "ApplicationGallery.js"
                        }
                    }
                },
                // 软件部文档页面
                "SoftwareDocument": {
                    "lastModify": "Fri Aug 31 2018 16:34:44 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1535704484000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "awesomplete-mailSetting-a439ec29a7.min.css": {
                            "referenceOrder": 1,
                            "filePath": "css/extends/integrationLibs/awesomplete-mailSetting-a439ec29a7.min.css",
                            "mergeToParentFile": null
                        },
                        "global_table_style.css": {
                            "referenceOrder": 2,
                            "filePath": "css/global/global_table_style.css",
                            "mergeToParentFile": "SoftwareDocument.css"
                        },
                        "SoftwareDocument.css": {
                            "referenceOrder": 3,
                            "filePath": "css/SoftwareDocument.css",
                            "mergeToParentFile": "SoftwareDocument.css"
                        },
                        "eoulu_ul_reset.css": {
                            "referenceOrder": 4,
                            "filePath": "css/global/eoulu_ul_reset.css",
                            "mergeToParentFile": "SoftwareDocument.css"
                        },
                        "time_line.css": {
                            "referenceOrder": 5,
                            "filePath": "css/modules/software/time_line.css",
                            "mergeToParentFile": "SoftwareDocument.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "jquery-cookie-ajaxfile-77692a8173.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/integrationLibs/jquery-cookie-ajaxfile-77692a8173.min.js",
                            "mergeToParentFile": null
                        },
                        "bootstrap.min.js": {
                            "referenceOrder": 1,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox_all-56b86b3095.min.js": {
                            "referenceOrder": 2,
                            "filePath": "js/libs/integrationLibs/msgbox_all-56b86b3095.min.js",
                            "mergeToParentFile": "msgbox_all.js"
                        },
                        "awesomplete-mailSetting-ddfa04e4f1.min.js": {
                            "referenceOrder": 3,
                            "filePath": "js/libs/integrationLibs/awesomplete-mailSetting-ddfa04e4f1.min.js",
                            "mergeToParentFile": "awesomplete-mailSetting.js"
                        },
                        "responseLoading.js": {
                            "referenceOrder": 4,
                            "filePath": "js/global/responseLoading.js",
                            "mergeToParentFile": "SoftwareDocument.js"
                        },
                        "SoftwareDocument.js": {
                            "referenceOrder": 5,
                            "filePath": "js/SoftwareDocument.js",
                            "mergeToParentFile": "SoftwareDocument.js"
                        }
                    }
                },
                // 物流统计页面
                "Transport": {
                    "lastModify": "Tue Sep 04 2018 17:49:45 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536054585000",
                    // css
                    "cssPrimary": {},
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-form-button-res-icon-list.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-form-button-res-icon-list.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox.js": {
                            "referenceOrder": 1,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "responseLoading.js": {
                            "referenceOrder": 2,
                            "filePath": "js/global/responseLoading.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "transport.js": {
                            "referenceOrder": 3,
                            "filePath": "js/transport.js",
                            "mergeToParentFile": "transport.js"
                        }
                    }
                },
                "GetOrderRoute": {
                    "lastModify": "Tue Sep 04 2018 17:49:45 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536054585000",
                    // css
                    "cssPrimary": {},
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-form-button-res-icon-list.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-form-button-res-icon-list.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox.js": {
                            "referenceOrder": 1,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "responseLoading.js": {
                            "referenceOrder": 2,
                            "filePath": "js/global/responseLoading.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "transport.js": {
                            "referenceOrder": 3,
                            "filePath": "js/transport.js",
                            "mergeToParentFile": "transport.js"
                        }
                    }
                },
                "GetOrderByPageOne": {
                    "lastModify": "Tue Sep 04 2018 17:49:45 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536054585000",
                    // css
                    "cssPrimary": {},
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-form-button-res-icon-list.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-form-button-res-icon-list.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox.js": {
                            "referenceOrder": 1,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "responseLoading.js": {
                            "referenceOrder": 2,
                            "filePath": "js/global/responseLoading.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "transport.js": {
                            "referenceOrder": 3,
                            "filePath": "js/transport.js",
                            "mergeToParentFile": "transport.js"
                        }
                    }
                },
                "GetOrderByPageTwo": {
                    "lastModify": "Tue Sep 04 2018 17:49:45 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536054585000",
                    // css
                    "cssPrimary": {},
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-form-button-res-icon-list.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-form-button-res-icon-list.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox.js": {
                            "referenceOrder": 1,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "responseLoading.js": {
                            "referenceOrder": 2,
                            "filePath": "js/global/responseLoading.js",
                            "mergeToParentFile": "transport.js"
                        },
                        "transport.js": {
                            "referenceOrder": 3,
                            "filePath": "js/transport.js",
                            "mergeToParentFile": "transport.js"
                        }
                    }
                },
                // 物流统计页面结束
                // 付款申请页面
                "PaymentRequest": {
                    "lastModify": "Wed Sep 05 2018 13:30:05 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536125405000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "PaymentRequest.css": {
                            "referenceOrder": 1,
                            "filePath": "css/modules/personnel/PaymentRequest.css",
                            "mergeToParentFile": "PaymentRequest.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "PaymentRequest.js": {
                            "referenceOrder": 0,
                            "filePath": "js/modules/personnel/PaymentRequest.js",
                            "mergeToParentFile": "PaymentRequest.js"
                        }
                    }
                },
                // 付款申请页面結束
                // 服务完成报告页面
                "ServiceReport": {
                    "lastModify": "Thu Sep 06 2018 11:38:45 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536205125000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "eouluCustom.css": {
                            "referenceOrder": 1,
                            "filePath": "css/global/eouluCustom.css",
                            "mergeToParentFile": "ServiceReport.css"
                        },
                        "ServiceReport.css": {
                            "referenceOrder": 2,
                            "filePath": "css/ServiceReport.css",
                            "mergeToParentFile": "ServiceReport.css"
                        },
                        "dispatchLoading.css": {
                            "referenceOrder": 3,
                            "filePath": "css/global/dispatchLoading.css",
                            "mergeToParentFile": "ServiceReport.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "jSignature.min.js": {
                            "referenceOrder": 1,
                            "filePath": "js/libs/jSignature.min.js",
                            "mergeToParentFile": "ServiceReport.js"
                        },
                        "colResizable-1.6.min.js": {
                            "referenceOrder": 2,
                            "filePath": "plugins/colResizable/colResizable-1.6.min.js",
                            "mergeToParentFile": "ServiceReport.js"
                        },
                        "ServiceReport.js": {
                            "referenceOrder": 3,
                            "filePath": "js/ServiceReport.js",
                            "mergeToParentFile": "ServiceReport.js"
                        }
                    }
                },
                // 服务完成报告页面结束
                // 考核明细页面
                "AssessmentStatistics": {
                    "lastModify": "Fri Sep 07 2018 13:06:28 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536296788000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "dispatchLoading.css": {
                            "referenceOrder": 1,
                            "filePath": "css/global/dispatchLoading.css",
                            "mergeToParentFile": "examination.css"
                        },
                        "bootstrap-multiselect.css": {
                            "referenceOrder": 2,
                            "filePath": "css/libs/bootstrap-multiselect.css",
                            "mergeToParentFile": "examination.css"
                        },
                        "eouluCustom.css": {
                            "referenceOrder": 3,
                            "filePath": "css/global/eouluCustom.css",
                            "mergeToParentFile": "examination.css"
                        },
                        "examination.css": {
                            "referenceOrder": 4,
                            "filePath": "css/examination.css",
                            "mergeToParentFile": "examination.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "echarts-4.1.0.rc2.min.js": {
                            "referenceOrder": 1,
                            "filePath": "js/libs/echarts/echarts-4.1.0.rc2.min.js",
                            "mergeToParentFile": null
                        },
                        "bootstrap-multiselect.js": {
                            "referenceOrder": 2,
                            "filePath": "js/libs/bootstrap-multiselect.js",
                            "mergeToParentFile": "examination.js"
                        },
                        "eoulu_chart_1.js": {
                            "referenceOrder": 3,
                            "filePath": "plugins/echarts/theme/eoulu_chart_1.js",
                            "mergeToParentFile": "examination.js"
                        },
                        "examination.js": {
                            "referenceOrder": 4,
                            "filePath": "js/examination.js",
                            "mergeToParentFile": "examination.js"
                        }
                    }
                },
                // 考核明细页面结束
                // 装机进展页面
                "Hardware": {
                    "lastModify": "Tue Sep 11 2018 16:39:08 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536655148000",
                    // css
                    "cssPrimary": {
                        "bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css",
                            "mergeToParentFile": null
                        },
                        "awesomplete_all-a2ac84f236.min.css": {
                            "referenceOrder": 1,
                            "filePath": "plugins/awesomplete/awesomplete_all-a2ac84f236.min.css",
                            "mergeToParentFile": null
                        },
                        "global_table_style.css": {
                            "referenceOrder": 2,
                            "filePath": "css/global/global_table_style.css",
                            "mergeToParentFile": "hardware.css"
                        },
                        "hardware.css": {
                            "referenceOrder": 3,
                            "filePath": "css/hardware.css",
                            "mergeToParentFile": "hardware.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js",
                            "mergeToParentFile": null
                        },
                        "awesomplete.min.js": {
                            "referenceOrder": 1,
                            "filePath": "plugins/awesomplete/awesomplete.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox.js": {
                            "referenceOrder": 2,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "hardware.js"
                        },
                        "hardware.js": {
                            "referenceOrder": 3,
                            "filePath": "js/hardware.js",
                            "mergeToParentFile": "hardware.js"
                        }
                    }
                },
                "GetHardwareRoute": {
                    "lastModify": "Tue Sep 11 2018 16:39:08 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536655148000",
                    // css
                    "cssPrimary": {
                        "bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css",
                            "mergeToParentFile": null
                        },
                        "awesomplete_all-a2ac84f236.min.css": {
                            "referenceOrder": 1,
                            "filePath": "plugins/awesomplete/awesomplete_all-a2ac84f236.min.css",
                            "mergeToParentFile": null
                        },
                        "global_table_style.css": {
                            "referenceOrder": 2,
                            "filePath": "css/global/global_table_style.css",
                            "mergeToParentFile": "hardware.css"
                        },
                        "hardware.css": {
                            "referenceOrder": 3,
                            "filePath": "css/hardware.css",
                            "mergeToParentFile": "hardware.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js",
                            "mergeToParentFile": null
                        },
                        "awesomplete.min.js": {
                            "referenceOrder": 1,
                            "filePath": "plugins/awesomplete/awesomplete.min.js",
                            "mergeToParentFile": null
                        },
                        "msgbox.js": {
                            "referenceOrder": 2,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "hardware.js"
                        },
                        "hardware.js": {
                            "referenceOrder": 3,
                            "filePath": "js/hardware.js",
                            "mergeToParentFile": "hardware.js"
                        }
                    }
                },
                // 装机进展页面结束
                // 标准产品页面
                "StandardProduct": {
                    "lastModify": "Tue Sep 11 2018 17:04:24 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536656664000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "eouluCustom.css": {
                            "referenceOrder": 1,
                            "filePath": "css/global/eouluCustom.css",
                            "mergeToParentFile": "StandardProduct.css"
                        },
                        "StandardProduct.css": {
                            "referenceOrder": 2,
                            "filePath": "css/StandardProduct.css",
                            "mergeToParentFile": "StandardProduct.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "responseLoading.js": {
                            "referenceOrder": 1,
                            "filePath": "js/global/responseLoading.js",
                            "mergeToParentFile": "StandardProduct.js"
                        },
                        "StandardProduct.js": {
                            "referenceOrder": 2,
                            "filePath": "js/StandardProduct.js",
                            "mergeToParentFile": "StandardProduct.js"
                        }
                    }
                },
                // 标准产品页面结束
                // 客户信息表页面
                "Customer": {
                    "lastModify": "Thu Sep 13 2018 13:19:23 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536815963000",
                    // css
                    "cssPrimary": {
                        "bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.css",
                            "mergeToParentFile": null
                        },
                        "customer.css": {
                            "referenceOrder": 1,
                            "filePath": "css/modules/commerce/customer.css",
                            "mergeToParentFile": "customer.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.js",
                            "mergeToParentFile": null
                        },
                        "customer.js": {
                            "referenceOrder": 1,
                            "filePath": "js/modules/commerce/customer.js",
                            "mergeToParentFile": "customer.js"
                        }
                    }
                },
                "GetCustomerInfo2": {
                    "lastModify": "Thu Sep 13 2018 13:19:23 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536815963000",
                    // css
                    "cssPrimary": {
                        "bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.css",
                            "mergeToParentFile": null
                        },
                        "customer.css": {
                            "referenceOrder": 1,
                            "filePath": "css/modules/commerce/customer.css",
                            "mergeToParentFile": "customer.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap/bootstrap-grid-table-form-btn-res-icon-advancedbtn.min.js",
                            "mergeToParentFile": null
                        },
                        "customer.js": {
                            "referenceOrder": 1,
                            "filePath": "js/modules/commerce/customer.js",
                            "mergeToParentFile": "customer.js"
                        }
                    }
                },
                // 客户信息表页面结束
                // 所有设备清单页面
                "AllLab": {
                    "lastModify": "Fri Sep 14 2018 13:25:39 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536902739000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "Lab.css": {
                            "referenceOrder": 1,
                            "filePath": "css/modules/laboratory/Lab.css",
                            "mergeToParentFile": "Lab.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "Lab.js": {
                            "referenceOrder": 1,
                            "filePath": "js/modules/laboratory/Lab.js",
                            "mergeToParentFile": "Lab.js"
                        }
                    }
                },
                // 所有设备清单页面结束
                // 设备清单页面
                "Lab": {
                    "lastModify": "Fri Sep 14 2018 13:25:39 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1536902739000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "Lab.css": {
                            "referenceOrder": 1,
                            "filePath": "css/modules/laboratory/Lab.css",
                            "mergeToParentFile": "Lab.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "Lab.js": {
                            "referenceOrder": 1,
                            "filePath": "js/modules/laboratory/Lab.js",
                            "mergeToParentFile": "Lab.js"
                        }
                    }
                },
                // 设备清单页面结束
                // 报销申请页面
                "Reimburse": {
                    "lastModify": "Tue Sep 18 2018 17:29:07 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1537262947000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "reimburse.css": {
                            "referenceOrder": 1,
                            "filePath": "css/modules/personnel/reimburse.css",
                            "mergeToParentFile": "reimburse.css"
                        }
                    },
                    // js
                    "jsPrimary": {
                        "bootstrap.min.js": {
                            "referenceOrder": 0,
                            "filePath": "js/libs/bootstrap.min.js",
                            "mergeToParentFile": null
                        },
                        "reimburse.js": {
                            "referenceOrder": 1,
                            "filePath": "js/modules/personnel/reimburse.js",
                            "mergeToParentFile": "reimburse.js"
                        }
                    }
                },
                // 报销申请页面结束
                // 员工行程页面
                "Schedule": {
                    "lastModify": "Tue Sep 18 2018 17:29:07 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1537262947000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "daterangepicker.css": {
                            "referenceOrder": 1,
                            "filePath": "css/libs/daterangepicker.css",
                            "mergeToParentFile": null
                        },
                        "awesomplete_all-a2ac84f236.min.css": {
                            "referenceOrder": 2,
                            "filePath": "plugins/awesomplete/awesomplete_all-a2ac84f236.min.css",
                            "mergeToParentFile": null
                        },
                        "kalendae_pc.css": {
                            "referenceOrder": 3,
                            "filePath": "css/libs/kalendae_pc.css",
                            "mergeToParentFile": "schedule.css"
                        },
                        "schedule.css": {
                            "referenceOrder": 4,
                            "filePath": "css/modules/serviced/schedule.css",
                            "mergeToParentFile": "schedule.css"
                        },
                        "fullcalendar.css": {
                            "referenceOrder": 5,
                            "filePath": "css/fullcalendar.css",
                            "mergeToParentFile": null
                        }
                    },
                    // js
                    "jsPrimary": {
                        "http://api.map.baidu.com/api?v=2.0&ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV": {
                            "referenceOrder": 0,
                            "filePath": "http://api.map.baidu.com/api?v=2.0&ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV",
                            "mergeToParentFile": null
                        },
                        "echarts-all-min.js": {
                            "referenceOrder": 1,
                            "filePath": "plugins/echarts/map_can/echarts-all-min.js",
                            "mergeToParentFile": null
                        },
                        "bootstrap-moment-daterangepicker-abd2349e95.min.js": {
                            "referenceOrder": 2,
                            "filePath": "js/libs/integrationLibs/bootstrap-moment-daterangepicker-abd2349e95.min.js",
                            "mergeToParentFile": null
                        },
                        "scrolltopcontrol.js": {
                            "referenceOrder": 3,
                            "filePath": "js/libs/scrolltopcontrol.js",
                            "mergeToParentFile": null
                        },
                        "fullcalendar.min.js": {
                            "referenceOrder": 4,
                            "filePath": "js/fullcalendar.min.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "kalendae.standalone_zh.js": {
                            "referenceOrder": 5,
                            "filePath": "js/libs/kalendae.standalone_zh.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "awesomplete.min.js": {
                            "referenceOrder": 6,
                            "filePath": "plugins/awesomplete/awesomplete.min.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "msgbox.js": {
                            "referenceOrder": 7,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "schedule.js": {
                            "referenceOrder": 8,
                            "filePath": "js/modules/serviced/schedule.js",
                            "mergeToParentFile": "schedule.js"
                        }
                    }
                },
                "ScheduleRoute": {
                    "lastModify": "Tue Sep 18 2018 17:29:07 GMT+0800 (中国标准时间)",
                    "lastModifyTime": "1537262947000",
                    // css
                    "cssPrimary": {
                        "bootstrap.min.css": {
                            "referenceOrder": 0,
                            "filePath": "css/libs/bootstrap.min.css",
                            "mergeToParentFile": null
                        },
                        "daterangepicker.css": {
                            "referenceOrder": 1,
                            "filePath": "css/libs/daterangepicker.css",
                            "mergeToParentFile": null
                        },
                        "awesomplete_all-a2ac84f236.min.css": {
                            "referenceOrder": 2,
                            "filePath": "plugins/awesomplete/awesomplete_all-a2ac84f236.min.css",
                            "mergeToParentFile": null
                        },
                        "kalendae_pc.css": {
                            "referenceOrder": 3,
                            "filePath": "css/libs/kalendae_pc.css",
                            "mergeToParentFile": "schedule.css"
                        },
                        "schedule.css": {
                            "referenceOrder": 4,
                            "filePath": "css/modules/serviced/schedule.css",
                            "mergeToParentFile": "schedule.css"
                        },
                        "fullcalendar.css": {
                            "referenceOrder": 5,
                            "filePath": "css/fullcalendar.css",
                            "mergeToParentFile": null
                        }
                    },
                    // js
                    "jsPrimary": {
                        "http://api.map.baidu.com/api?v=2.0&ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV": {
                            "referenceOrder": 0,
                            "filePath": "http://api.map.baidu.com/api?v=2.0&ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV",
                            "mergeToParentFile": null
                        },
                        "echarts-all-min.js": {
                            "referenceOrder": 1,
                            "filePath": "plugins/echarts/map_can/echarts-all-min.js",
                            "mergeToParentFile": null
                        },
                        "bootstrap-moment-daterangepicker-abd2349e95.min.js": {
                            "referenceOrder": 2,
                            "filePath": "js/libs/integrationLibs/bootstrap-moment-daterangepicker-abd2349e95.min.js",
                            "mergeToParentFile": null
                        },
                        "scrolltopcontrol.js": {
                            "referenceOrder": 3,
                            "filePath": "js/libs/scrolltopcontrol.js",
                            "mergeToParentFile": null
                        },
                        "fullcalendar.min.js": {
                            "referenceOrder": 4,
                            "filePath": "js/fullcalendar.min.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "kalendae.standalone_zh.js": {
                            "referenceOrder": 5,
                            "filePath": "js/libs/kalendae.standalone_zh.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "awesomplete.min.js": {
                            "referenceOrder": 6,
                            "filePath": "plugins/awesomplete/awesomplete.min.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "msgbox.js": {
                            "referenceOrder": 7,
                            "filePath": "js/msgbox.js",
                            "mergeToParentFile": "schedule.js"
                        },
                        "schedule.js": {
                            "referenceOrder": 8,
                            "filePath": "js/modules/serviced/schedule.js",
                            "mergeToParentFile": "schedule.js"
                        }
                    }
                },
                // 员工行程页面结束
            },
        // pageHrefImportFileMap 结束
        // compressFilePathMap 开始
        compressFilePathMap: {
            "cssMerge": {
                "awesomplete-mailSetting.css": {
                    "originPath": ["./cfChicken8/WebContent/plugins/awesomplete/awesomplete.css", "./cfChicken8/WebContent/plugins/awesomplete/awesomplete.theme.css", "./cfChicken8/WebContent/css/modules/software/mailSetting.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/extends/integrationLibs/",
                    "gulp-rev": "awesomplete-mailSetting-a439ec29a7.min.css",
                    "referencePath": "css/extends/integrationLibs/awesomplete-mailSetting-a439ec29a7.min.css",
                    "srcPath": "src/css/extends/integrationLibs/awesomplete-mailSetting.css"
                },
                "ApplicationGallery.css": {
                    "originPath": ["./cfChicken8/WebContent/css/global/global_table_style.css", "./cfChicken8/WebContent/css/modules/serviced/ApplicationGallery.css", "./cfChicken8/WebContent/css/global/add_update_section.css", "./cfChicken8/WebContent/css/global/eoulu_ul_reset.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/serviced/",
                    "gulp-rev": "ApplicationGallery-48a2d648b2.min.css",
                    "referencePath": "css/modules/serviced/ApplicationGallery-48a2d648b2.min.css",
                    "srcPath": "src/css/modules/serviced/ApplicationGallery.css"
                },
                "SoftwareDocument.css": {
                    "originPath": ["./cfChicken8/WebContent/css/global/global_table_style.css", "./cfChicken8/WebContent/css/SoftwareDocument.css", "./cfChicken8/WebContent/css/global/eoulu_ul_reset.css", "./cfChicken8/WebContent/css/modules/software/time_line.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/software/",
                    "gulp-rev": "SoftwareDocument-0dd1cea1d7.min.css",
                    "referencePath": "css/modules/software/SoftwareDocument-0dd1cea1d7.min.css",
                    "srcPath": "src/css/modules/software/SoftwareDocument.css"
                },
                "ServiceReport.css": {
                    "originPath": ["./cfChicken8/WebContent/css/global/eouluCustom.css", "./cfChicken8/WebContent/css/ServiceReport.css", "./cfChicken8/WebContent/css/global/dispatchLoading.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/serviced/",
                    "gulp-rev": "ServiceReport-30eec74d24.min.css",
                    "referencePath": "css/modules/serviced/ServiceReport-30eec74d24.min.css",
                    "srcPath": "src/css/modules/serviced/ServiceReport.css"
                },
                "examination.css": {
                    "originPath": ["./cfChicken8/WebContent/css/global/dispatchLoading.css", "./cfChicken8/WebContent/css/libs/bootstrap-multiselect.css", "./cfChicken8/WebContent/css/global/eouluCustom.css", "./cfChicken8/WebContent/css/examination.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/personnel/",
                    "gulp-rev": "examination-3a846d3f0d.min.css",
                    "referencePath": "css/modules/personnel/examination-3a846d3f0d.min.css",
                    "srcPath": "src/css/modules/personnel/examination.css"
                },
                "hardware.css": {
                    "originPath": ["./cfChicken8/WebContent/css/global/global_table_style.css", "./cfChicken8/WebContent/css/hardware.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/serviced/",
                    "gulp-rev": "hardware-b64078737b.min.css",
                    "referencePath": "css/modules/serviced/hardware-b64078737b.min.css",
                    "srcPath": "src/css/modules/serviced/hardware.css"
                },
                "StandardProduct.css": {
                    "originPath": ["./cfChicken8/WebContent/css/global/eouluCustom.css", "./cfChicken8/WebContent/css/StandardProduct.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/serviced/",
                    "gulp-rev": "StandardProduct-855edafc10.min.css",
                    "referencePath": "css/modules/serviced/StandardProduct-855edafc10.min.css",
                    "srcPath": "src/css/modules/serviced/StandardProduct.css"
                },
                "customer.css": {
                    "originPath": ["./cfChicken8/WebContent/css/modules/commerce/customer.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/commerce/",
                    "gulp-rev": "customer-2d46b78604.min.css",
                    "referencePath": "css/modules/commerce/customer-2d46b78604.min.css",
                    "srcPath": "src/css/modules/commerce/customer.css"
                },
                "Lab.css": {
                    "originPath": ["./cfChicken8/WebContent/css/modules/laboratory/Lab.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/laboratory/",
                    "gulp-rev": "Lab-ecd0227fc6.min.css",
                    "referencePath": "css/modules/laboratory/Lab-ecd0227fc6.min.css",
                    "srcPath": "src/css/modules/laboratory/Lab.css"
                },
                "reimburse.css": {
                    "originPath": ["./cfChicken8/WebContent/css/modules/personnel/reimburse.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/personnel/",
                    "gulp-rev": "reimburse-c0f068a8f6.min.css",
                    "referencePath": "css/modules/personnel/reimburse-c0f068a8f6.min.css",
                    "srcPath": "src/css/modules/personnel/reimburse.css"
                },
                "PaymentRequest.css": {
                    "originPath": ["./cfChicken8/WebContent/css/modules/personnel/PaymentRequest.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/personnel/",
                    "gulp-rev": "PaymentRequest-6bc602eb88.min.css",
                    "referencePath": "css/modules/personnel/PaymentRequest-6bc602eb88.min.css",
                    "srcPath": "src/css/modules/personnel/PaymentRequest.css"
                },
                "schedule.css": {
                    "originPath": ["./cfChicken8/WebContent/css/modules/serviced/schedule.css"],
                    "outBasePath": "./cfChicken8/WebContent/css/modules/serviced/",
                    "gulp-rev": "schedule-0f26355e98.min.css",
                    "referencePath": "css/modules/serviced/schedule-0f26355e98.min.css",
                    "srcPath": "src/css/modules/serviced/schedule.css"
                }
            },
            "jsMerge": {
                "top-dbec435863.min.js": {
                    "originPath": ["./cfChicken8/WebContent/js/libs/jquery-3.3.1.min.js", "./cfChicken8/WebContent/plugins/ecdo/ec-do-1.1.4.min.js", "./cfChicken8/WebContent/plugins/cookie/jquery.cookie.js", "./cfChicken8/WebContent/plugins/imageResizeTool/imageResizeTool.min.js", "./cfChicken8/WebContent/js/msgbox_unload.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/sharing/",
                    "gulp-rev": "top-dbec435863.min.js",
                    "referencePath": "js/modules/sharing/top-dbec435863.min.js",
                    "srcPath": "src/js/modules/sharing/top-dbec435863.min.js"
                },
                "jquery-cookie-ajaxfile.js": {
                    "originPath": ["./cfChicken8/WebContent/js/jquery-1.12.3.min.js", "./cfChicken8/WebContent/plugins/cookie/jquery.cookie.js", "./cfChicken8/WebContent/js/ajaxfileupload.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/libs/integrationLibs/",
                    "gulp-rev": "jquery-cookie-ajaxfile-77692a8173.min.js",
                    "referencePath": "js/libs/integrationLibs/jquery-cookie-ajaxfile-77692a8173.min.js",
                    "srcPath": "src/js/libs/integrationLibs/jquery-cookie-ajaxfile-77692a8173.min.js"
                },
                "msgbox_all.js": {
                    "originPath": ["./cfChicken8/WebContent/js/msgbox.js", "./cfChicken8/WebContent/js/msgbox_unload.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/libs/integrationLibs/",
                    "gulp-rev": "msgbox_all-56b86b3095.min.js",
                    "referencePath": "js/libs/integrationLibs/msgbox_all-56b86b3095.min.js",
                    "srcPath": "src/js/libs/integrationLibs/msgbox_all-56b86b3095.min.js"
                },
                "awesomplete-mailSetting.js": {
                    "originPath": ["./cfChicken8/WebContent/plugins/awesomplete/awesomplete.min.js", "./cfChicken8/WebContent/js/modules/software/mailSetting.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/libs/integrationLibs/",
                    "gulp-rev": "awesomplete-mailSetting-ddfa04e4f1.min.js",
                    "referencePath": "js/libs/integrationLibs/awesomplete-mailSetting-ddfa04e4f1.min.js",
                    "srcPath": "src/js/libs/integrationLibs/awesomplete-mailSetting-ddfa04e4f1.min.js"
                },
                "ApplicationGallery.js": {
                    "originPath": ["./cfChicken8/WebContent/js/modules/serviced/ApplicationGallery.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/serviced/",
                    "gulp-rev": "ApplicationGallery-bb5a55da9e.min.js",
                    "referencePath": "js/modules/serviced/ApplicationGallery-bb5a55da9e.min.js",
                    "srcPath": "src/js/modules/serviced/ApplicationGallery-bb5a55da9e.min.js"
                },
                "dispatchScript.js": {
                    "originPath": ["./cfChicken8/WebContent/js/global/dispatchScript.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/global/",
                    "gulp-rev": "dispatchScript-a9a7544bda.min.js",
                    "referencePath": "js/global/dispatchScript-a9a7544bda.min.js",
                    "srcPath": "src/js/global/dispatchScript-a9a7544bda.min.js"
                },
                "SoftwareDocument.js": {
                    "originPath": ["./cfChicken8/WebContent/js/global/responseLoading.js", "./cfChicken8/WebContent/js/SoftwareDocument.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/software/",
                    "gulp-rev": "SoftwareDocument-698d116bb8.min.js",
                    "referencePath": "js/modules/software/SoftwareDocument-698d116bb8.min.js",
                    "srcPath": "src/js/modules/software/SoftwareDocument-698d116bb8.min.js"
                },
                "transport.js": {
                    "originPath": ["./cfChicken8/WebContent/js/msgbox.js", "./cfChicken8/WebContent/js/global/responseLoading.js", "./cfChicken8/WebContent/js/transport.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/transport/",
                    "gulp-rev": "transport-0082f74523.min.js",
                    "referencePath": "js/modules/transport/transport-0082f74523.min.js",
                    "srcPath": "src/js/modules/transport/transport-0082f74523.min.js"
                },
                "ServiceReport.js": {
                    "originPath": ["./cfChicken8/WebContent/js/libs/jSignature.min.js", "./cfChicken8/WebContent/plugins/colResizable/colResizable-1.6.min.js", "./cfChicken8/WebContent/js/ServiceReport.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/serviced/",
                    "gulp-rev": "ServiceReport-2951b6ba44.min.js",
                    "referencePath": "js/modules/serviced/ServiceReport-2951b6ba44.min.js",
                    "srcPath": "src/js/modules/serviced/ServiceReport-2951b6ba44.min.js"
                },
                "examination.js": {
                    "originPath": ["./cfChicken8/WebContent/js/libs/bootstrap-multiselect.js", "./cfChicken8/WebContent/plugins/echarts/theme/eoulu_chart_1.js", "./cfChicken8/WebContent/js/examination.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/personnel/",
                    "gulp-rev": "examination-179cada3b8.min.js",
                    "referencePath": "js/modules/personnel/examination-179cada3b8.min.js",
                    "srcPath": "src/js/modules/personnel/examination-179cada3b8.min.js"
                },
                "hardware.js": {
                    "originPath": ["./cfChicken8/WebContent/js/msgbox.js", "./cfChicken8/WebContent/js/hardware.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/serviced/",
                    "gulp-rev": "hardware-f19ddd9986.min.js",
                    "referencePath": "js/modules/serviced/hardware-f19ddd9986.min.js",
                    "srcPath": "src/js/modules/serviced/hardware-f19ddd9986.min.js"
                },
                "StandardProduct.js": {
                    "originPath": ["./cfChicken8/WebContent/js/global/responseLoading.js", "./cfChicken8/WebContent/js/StandardProduct.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/serviced/",
                    "gulp-rev": "StandardProduct-acbe9a200f.min.js",
                    "referencePath": "js/modules/serviced/StandardProduct-acbe9a200f.min.js",
                    "srcPath": "src/js/modules/serviced/StandardProduct-acbe9a200f.min.js"
                },
                "customer.js": {
                    "originPath": ["./cfChicken8/WebContent/js/modules/commerce/customer.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/commerce/",
                    "gulp-rev": "customer-a03b5a60b3.min.js",
                    "referencePath": "js/modules/commerce/customer-a03b5a60b3.min.js",
                    "srcPath": "src/js/modules/commerce/customer-a03b5a60b3.min.js"
                },
                "Lab.js": {
                    "originPath": ["./cfChicken8/WebContent/js/modules/laboratory/Lab.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/laboratory/",
                    "gulp-rev": "Lab-4be8c6a8d3.min.js",
                    "referencePath": "js/modules/laboratory/Lab-4be8c6a8d3.min.js",
                    "srcPath": "src/js/modules/laboratory/Lab-4be8c6a8d3.min.js"
                },
                "reimburse.js": {
                    "originPath": ["./cfChicken8/WebContent/js/modules/personnel/reimburse.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/personnel/",
                    "gulp-rev": "reimburse-d7048b79a9.min.js",
                    "referencePath": "js/modules/personnel/reimburse-d7048b79a9.min.js",
                    "srcPath": "src/js/modules/personnel/reimburse-d7048b79a9.min.js"
                },
                "PaymentRequest.js": {
                    "originPath": ["./cfChicken8/WebContent/js/modules/personnel/PaymentRequest.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/personnel/",
                    "gulp-rev": "PaymentRequest-4b3d5152bd.min.js",
                    "referencePath": "js/modules/personnel/PaymentRequest-4b3d5152bd.min.js",
                    "srcPath": "src/js/modules/personnel/PaymentRequest-4b3d5152bd.min.js"
                },
                "schedule.js": {
                    "originPath": ["./cfChicken8/WebContent/js/modules/serviced/schedule.js"],
                    "outBasePath": "./cfChicken8/WebContent/js/modules/serviced/",
                    "gulp-rev": "schedule-4d6a516990.min.js",
                    "referencePath": "js/modules/serviced/schedule-4d6a516990.min.js",
                    "srcPath": "src/js/modules/serviced/schedule.js"
                }
            }
        }
        // compressFilePathMap 结束
    };

    // 定义一些api
    
    var eouluGlobal_API = {
    /* 这里定义可以链式调用的，以C_开头 */
        // C_firstFunc: function(str = _DefaultParam.projectName){
        //     var aa = $("<h2>");
        //     aa.append(str);
        //     $("body").empty().append(aa);
        //     return this;//返回当前方法
        // },
        // C_secondFunc: function(){
        //     $("body").append(this.S_ab());
        //     $("body").append($("<h1>223344556677</h1>"));
        //     return this;//返回当前方法
        // },
        C_setProjectName: function(str){
            _DefaultParam.projectName = str;
            return this; //返回当前方法，即 eouluGlobal对象
        },
        // 按钮不可点击
        C_btnDisabled: function(JQObj, isChangeText, newText){
            JQObj.css("cursor","not-allowed").prop("disabled","disabled");
            if(isChangeText){
                // var element = document.getElementById("p"); var name = element.tagName;
                // var name = $("#p").get(0).tagName;
                // var name = $("#p")[0].tagName;
                // var name = $("#p").prop("tagName");
                // var name = $("#p").prop("nodeName");
                var iTagName = JQObj.prop("nodeName").toLocaleUpperCase();
                if(iTagName == "INPUT"){
                    JQObj.val(newText);
                }else if(iTagName == "BUTTON" || iTagName == "A"){
                    JQObj.text(newText);
                }
            }
            return this;
        },
        // 按钮可以点击
        C_btnAbled: function(JQObj, isChangeText, newText){
            JQObj.css("cursor","pointer").prop("disabled",false);
            if(isChangeText){
                var iTagName = JQObj.prop("nodeName").toLocaleUpperCase();
                if(iTagName == "INPUT"){
                    JQObj.val(newText);
                }else if(iTagName == "BUTTON" || iTagName == "A"){
                    JQObj.text(newText);
                }
            }
            return this;
        },

    /* 这里定义不可以链式调用的，以S_开头 */
        // 同步或异步动态加载脚本
        // @param  success(response,status) --- response - 包含来自请求的结果数据;status - 包含请求的状态（"success", "notmodified", "error", "timeout" 或 "parsererror"）
        // @param scriptCharset --- 要求为String类型的参数，只有当请求时dataType为"jsonp"或者"script"，并且type是GET时才会用于强制修改字符集(charset)。通常在本地和远程的内容编码不同时使用。这里默认设置'utf-8'
        S_loadScript: function(iurl, async, cache, beforeSend, dataFilter, success, error, complete, scriptCharset){
            var eouluGlobalThis = this;
            iurl = iurl || "";
            if(async === undefined || async === null){
                async = true;
            }else{
                async = async;
            }
            // cache 默认为true（当dataType为script时，默认为false），设置为false将不会从浏览器缓存中加载请求信息
            if(cache === undefined || cache === null){
                cache = false;
            }else{
                cache = cache;
            }
            // 有时候有些项目需要include新闻发布系统某站点数据时，新闻发布系统该站点配置为gb2312编码的站点，此时，页面设置的编码必须为gb2312。但ajax异步获取的utf-8信息需要写入页面，与页面编码不一致，显示为乱码
                // 解决方法：添加ajax参数：scriptCharset:’utf-8’，使返回值以scriptCharset指定编码显示而不是默认以页面编码显示
            // 当异步请求的服务器页面的编码为 gb2312 时，此时不能使用ajax方法获取信息，因为ajax内部是使用unicode 按照utf8编码来处理所有字符的。所以返回的信息就乱码了
                // 解决方法：使用隐藏的iframe加载页面，然后再获取目标数据到相应位置。
                // // html代码：
                // <div class="main">
                //     <div class="com-con"></div>
                // </div>
                // <iframe src="" id="iframe" style="display:none"></iframe>
                // // js代码：
                // var PAGE =(function(){
                //     $iframe = $('#iframe'),
                //     fn = {
                //         init : function(){ /*初始*/
                //             $iframe.attr('src',url);
                //             $iframe.load(function(){                 
                //                 var $data;   
                //                 try{
                //                     $data = $iframe.contents();
                //                 }catch(e){
                //                     return;
                //                 } 
                //                 $('.main .com-con').append($data.find('.com-con').html());
                //             });
                //         },
                //         getMore : function(url){ /*加载更多*/
                //             $iframe.attr('src',url);
                //             $data = $iframe.contents();
                //             $('.main .com-con').append($data.find('.com-con').html());
                //         }

                //     },
                //     init = function() {
                //         fn.init(); 
                //         /*点击获取更多*/
                //         $(".more-btn").bind("click",function(){
                //             /*..此处省略..*/
                //             fn.getMore(url);
                //         });
                //     };
                //     return{
                //          fn: fn,
                //          init: init
                //      }
                // })();

                // nie.define(function(){
                //     PAGE.init();
                // });

            scriptCharset = scriptCharset || "utf-8";
            $.ajax({
                url: iurl,
                async: async,
                cache: cache,
                dataType: "script",
                beforeSend: function(XMLHttpRequest){
                    var options = this;   //调用本次ajax请求时传递的options参数
                    if(eouluGlobalThis.S_isFunction(beforeSend)){
                        beforeSend(XMLHttpRequest, options);
                    }
                },
                dataFilter: function(data, type){
                    // 给Ajax返回的原始数据进行预处理的函数。它的调用在success之前。提供data和type两个参数：data是Ajax返回的原始数据，type是调用jQuery.ajax时提供的dataType参数。函数返回的值将由jQuery进一步处理。
                        // 当用户的session失效时可使用ajax请求时，可以使用这个函数进行判断是否要重新跳转到登录界面（系统的过滤器发现用户ajax的请求，但用户没有登录或session失效时返回字符串”timeOut"）：
                        // dataFilter: function(data, type){
                        //     console.log("data:"+data);
                        //     if(data == "timeOut" || data == "[object XMLDocument]"){//ajax请求，发现session过期，重新刷新页面，跳转到登录页面
                        //         window.location.reload();
                        //     }else{
                        //         return data;
                        //     }
                        // }

                    // 对Ajax返回的原始数据进行预处理
                    if(eouluGlobalThis.S_isFunction(dataFilter)){
                        var newData = dataFilter(data, type); // 函数需要有返回值
                        return newData; // 返回处理后的数据，处理后的数据将被其它函数使用（如success）
                    }else{
                        console.warn("dataFilter不是一个函数，data未做处理");
                        return data;
                    }
                },
                success: function(data, textStatus){
                    var options = this;   //调用本次ajax请求时传递的options参数
                    if(eouluGlobalThis.S_isFunction(success)){
                        success(data, textStatus, options);
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    //通常情况下textStatus和errorThrown只有其中一个包含信息
                    var options = this;   //调用本次ajax请求时传递的options参数
                    if(eouluGlobalThis.S_isFunction(error)){
                        error(XMLHttpRequest, textStatus, errorThrown, options);
                    }
                },
                complete: function(XMLHttpRequest, textStatus){
                    var options = this;   //调用本次ajax请求时传递的options参数
                    if(eouluGlobalThis.S_isFunction(complete)){
                        complete(XMLHttpRequest, textStatus, errorThrown, options);
                    }
                },
                scriptCharset: scriptCharset
            });
        },

        // @获取默认参数@
        S_getDefaultParam: function(){
            return _DefaultParam;
        },
        S_getEnvironment: function(){
            return _DefaultParam.environment;
        },
        S_getProjectName: function(){
            return _DefaultParam.projectName;
        },
        S_getBaseUrl: function(){
            return (window.location.href.split(_DefaultParam.projectName)[0]+_DefaultParam.projectName);
        },
        S_getHostName: function(){
            return (window.location.href.split(_DefaultParam.projectName)[0].substring(0,window.location.href.split(_DefaultParam.projectName)[0].length-1));
        },
        S_getCurPageHref: function(){
            var leaveHref = window.location.href.split(_DefaultParam.projectName+"/")[1];
            var curHref;
            if(leaveHref.indexOf("?")>-1){
                curHref = leaveHref.split("?")[0];
            }else{
                curHref = leaveHref;
            }
            return curHref;
        },
        S_getDispatchPageArr: function(){
            return _DefaultParam.canDispatchPageArr_href;
        },
        S_getNotEouluCopy: function(){
            return _DefaultParam.notEouluCopy_href;
        },
        S_getShowNavArr: function(){
            return _DefaultParam.showNavArr_href;
        },
        S_getPageAllConfig: function(){
            return _DefaultParam.pageAllConfig;
        },
        S_getDepart2PageObj: function(){
            return _DefaultParam.depart2PageObj;
        },
        // 获取pageHrefImportFileMap数据开始
        S_getPageHrefImportFileMap: function(){
            return _DefaultParam.pageHrefImportFileMap;
        },
        S_getCssJsPrimaryByHref: function(Href, Primary){
            if(Primary == undefined){
                console.warn("S_getCssJsPrimaryByHref的参数Primary未定义");
                return null;
            }
            var obj = null;
            $.each(_DefaultParam.pageHrefImportFileMap, function(name, value){
                if(name.toString() == Href.toString()){
                    var ikey = Primary.toString();
                    if(value[ikey] != undefined){
                        obj = value[ikey];
                    }
                    return false;
                }
            });
            return obj;
        },
        S_getPageHrefImportFileMapAllKey: function(){
            return Object.getOwnPropertyNames(_DefaultParam.pageHrefImportFileMap);
        },
        // 获取pageHrefImportFileMap数据结束
        
        // 获取compressFilePathMap数据开始
        S_getCompressFilePathMap: function(){
            return _DefaultParam.compressFilePathMap;
        },
        S_getCompressFilePathMapLowerValue: function(Merge, fileName, ikey){
            if(Merge == undefined){
                console.warn("S_getCompressFilePathMapLowerValue的参数Merge未定义");
                return null;
            }
            if(fileName == undefined){
                console.warn("S_getCompressFilePathMapLowerValue的参数fileName未定义");
                return null;
            }
            var ivalue = null;
            var MergeKey = Merge.toString();
            if(_DefaultParam.compressFilePathMap[MergeKey] != undefined){
                var MergeValue = _DefaultParam.compressFilePathMap[MergeKey];
                $.each(MergeValue, function(name, value){
                    if(name.toString() == fileName.toString()){
                        if(ikey == undefined){
                            ivalue = value;
                        }else{
                            var param = ikey.toString();
                            if(value[param] != undefined){
                                ivalue = value[param];
                            }
                        }
                        return false;
                    }
                });
            }
            return ivalue;
        },
        // 获取compressFilePathMap数据结束
        
        // @URL处理类@
        // 设置URL，并跳转，@param isRead 是否只读，true为不跳转，只返回新url，false为跳转并返回新url
        // @param _blank，true为新页面，false为原页面；
        // @param ireplace，true为替换前一个历史记录，后退无效，false为不替换，可以后退
        S_settingURLParam: function(paramObj, isRead, _blank, ireplace){
            if(this.S_isObject(paramObj)){
                if(jQuery.isEmptyObject(paramObj)){
                    console.warn("C_setURLParam参数paramObj为空对象");
                    return false;
                }
                var paramStr = $.param(paramObj);
                var preHref = this.S_getBaseUrl()+"/"+this.S_getCurPageHref()+"?";
                var setHref = preHref+paramStr;
                if(!isRead){
                    if(_blank){
                        window.open(setHref);
                    }else{
                        if(!ireplace){
                            window.location.assign(setHref);
                        }else{
                            window.location.replace(setHref);
                        }
                    }
                }
                return setHref;
            }else{
                console.warn("C_setURLParam参数paramObj不是一个对象");
            }
        },

        // @数据处理类@
        // 判断变量是否是函数
        S_isFunction: function(obj){
            return Object.prototype.toString.call(obj) === '[object Function]';
        },
        // 判断变量是否是对象
        S_isObject: function(o){
            return Object.prototype.toString.call(o) === '[object Object]';
        }
        
    };

    // 最后将插件对象暴露给全局对象
    // 则最后闭包的实参不必传 jQuery, window, document
    _global = (function(){ return this || (0, eval)('this'); }());
    if (typeof module !== "undefined" && module.exports) {
        module.exports = eouluGlobal_API;
    } else if (typeof define === "function" && define.amd) {
        define(function(){return eouluGlobal_API;});
    } else {
        !('eouluGlobal' in _global) && (_global.eouluGlobal = eouluGlobal_API);
    }

    //这里确定了插件的名称
    // this.eouluGlobal = eouluGlobal_API;
})();


/***************************全局变量配置类****************************/
    var globalProjectName = "cfChicken8";
    var globalBaseUrl = window.location.href.split(globalProjectName)[0]+globalProjectName;
    var globalHostName = window.location.href.split(globalProjectName)[0].substring(0,window.location.href.split(globalProjectName)[0].length-1);

    // 版本号排除关键词、文件名或路径名，全文匹配
    var globalVersionExceptFileName = ["eoulu.ico","bootstrap","swiper-3.4.1.min.css","eouluCustom.css","reset.css","css/libs","css/extends","echarts","jquery","msgbox","ajaxfileupload.js","html2canvas.js","fullcalendar.min.js","jsPdf.debug.js","underscore-min.js","ProvinceandCity.js","dispatchScript-a9a7544bda.min.js","js/libs","plugins/"];
    // 版本号
    var globalVersionNo = "1536897675563";

    // echarts自定义渐变色
    // 用法：series[i].itemStyle.normal.color =
    // function(params) {
    //     // console.log(params);
    //     // build a color map as your need.
    //     var colorList = [
    //       '#DE3656','#D6395B','#C93F64','#C0436C','#B64873','#A35283','#9D5588','#885F98','#7468A6','#6C6BA6','#5F72B6','#5A74B9','#5077BE','#4B7BC5','#467DC9','#4081CE','#3A83D3','#3183DA','#2A8BDF','#248EE3','#1E91E8','#1993EC','#1396F0','#0F98F4','#0B9AF7','#069CFA','#019FFF'
    //     ];
    //     return colorList[(params.dataIndex)%(colorList.length)];
    // }
    var globalColorList = [
              '#DE3656','#D6395B','#C93F64','#C0436C','#B64873','#A35283','#9D5588','#885F98','#7468A6','#6C6BA6','#5F72B6','#5A74B9','#5077BE','#4B7BC5','#467DC9','#4081CE','#3A83D3','#3183DA','#2A8BDF','#248EE3','#1E91E8','#1993EC','#1396F0','#0F98F4','#0B9AF7','#069CFA','#019FFF'
            ];

    // 隶变字体所用汉字
    var globalLiBianFontStr = "员工行程软件部商务物流统计箱单制作发票资料库FORMACT合同需求存页面服销售人事文档管理报价系采购运输指令保险热产品质量证明数重测试告熏蒸原地货通知验收客户信息表机台装进展后维修例拜访开项目询记录实施请假申任分配Keysight厂考核细完成供应标准设备清室";

    // 页面入口的麻将
    var globalIndexDepartmentMap = {
        "软件部":"image/index/department23.png",
        "商务部":"image/index/department24.png",
        "物流部":"image/index/department26.png",
        "物流统计":"image/index/department31.png",
        "合同统计":"image/index/department0.png",
        "服务部":"image/index/department20.png",
        "销售统计":"image/index/department18.png",
        "人事部":"image/index/department6.png",
        "实验室":"image/index/department4.png",
        "需求统计":"image/index/department15.png",
        "销售部":"image/index/department13.png"
    };

    // 全局所有部门数组（除软件部）
    var globalAllDepartArr = ["人事部","物流部","财务部","商务部","销售部","市场部","服务部","硬件部","应用部","标准服务部","研发部","厦门办事处"];
    // 全局Eoulu所有部门
    var globalEouluAllDepart = ["软件部","财务部","人事部","商务部","销售部","市场部","服务部","物流部","硬件部","应用部","标准服务部","研发部","厦门办事处"];

    // 全局部门，部门与页面名称、链接映射
    var globalDepart2PageObj = {
        "物流部": {
            // "物流统计": "Transport?ActualDelivery=no&column=DateOfSign&condition=All",
            "物流统计": "Transport",
            "物流统计#": "GetOrderRoute",
            "物流统计##": "GetOrderByPageOne",
            "物流统计###": "GetOrderByPageTwo",
            // "库存采购": "StockPurchasing?ActualDelivery=no&column=DateOfSign&condition=All",
            "库存采购": "StockPurchasing",
            "库存采购#": "PriceRoute",
            "库存采购##": "GetOrderByPageOneInPrice",
            "库存页面": "Inventory",
            "FORMFACTOR": "OriginFactory",
            "FORMFACTOR#": "OriginFactorySearch",
            "箱单制作": "PackingList",
            "进出口运输指令": "Insurance",
            "国内运输指令": "TransportDirective",
            "运输保险": "Proposal",
            "供应商管理": "Supplier",
            "商品管理": "Commodity"
        },
        "商务部": {
            // "合同统计": "Price?ActualDelivery=no&column=DateOfSign&condition=All",
            "合同统计": "Price",
            "需求统计": "Requirement",
            "Keysight": "Keysight",
            "销售统计": "SalesStatistics",
            "热销产品": "HotProduct",
            "报价系统": "QuotationSystem",
            "发票制作": "Invoice",
            "质量证明": "Quality",
            "数量重量": "QuantityWeight",
            "测试报告": "TestReport",
            "熏蒸证明": "Fumigation",
            "原产地证明": "Origin",
            "验收报告": "Receiving",
            "发货通知": "Shipment",
            "FAT": "Acceptance",
            "客户信息表": "Customer",
            "客户信息表#": "GetCustomerInfo2",
            "原厂报价单": "OriginalQuotation",
            "工作汇报": "WorkReport",
            "招标文件": "BiddingDocument"
        },
        "服务部": {
            "机台统计": "MachineDetails",
            "装机进展": "Hardware",
            "装机进展#": "GetHardwareRoute",
            "售后维修": "AfterSale",
            "例行拜访": "RoutineVisit",
            "标准产品": "StandardProduct",
            "服务任务分配": "NonStandard",
            "员工行程": "Schedule",
            "员工行程#": "ScheduleRoute",
            "服务完成报告": "ServiceReport",
            "文档管理": "DocumentUpload",
            "研发图库": "ApplicationGallery"
        },
        "软件部": {
            "软件部文档": "SoftwareDocument",
            "开发项目管理": "SoftwareProject",
            "软件产品管理": "SoftwareProduct",
            "客户询价记录": "CustomerInquiry",
            "软件实施管理": "SoftwareImplementation"
        },
        "人事部": {
            "员工信息": "StaffInfo",
            "请假申请": "LeaveApplication",
            "人事任务分配": "Tasking",
            "考核明细": "AssessmentStatistics",
            "报销申请": "Reimburse",
            "付款申请": "PaymentRequest"
        },
        "销售部": {
            "销售报价系统": "SalesQuotationSystem"
        },
        "实验室": {
            "所有设备清单": "AllLab",
            "设备清单": "Lab"
        }
    };

    // FORMFACTOR页面仓库地址
    var globalFFWarehouse = ["请选择仓库地址","HK EOULU TRADING LIMITED","Awot Global Express (HK) Ltd","Shing Fat Logistics Ltd","Other"];
    // FORMFACTOR页面仓库地址与TO、CC映射
    var globalFFWarehouseTOCC = [{"TO":[""],"CC":[""]},
    {"TO":["beijing@hkgcr.com","hongkong@hkgcr.com"],"CC":["jiangyaping@eoulu.com","zhaona@eoulu.com","zhaowenzhen@eoulu.com"]},
    {"TO":["lydia_liu@awotglobal.com"],"CC":["jiangyaping@eoulu.com","zhaona@eoulu.com","zhaowenzhen@eoulu.com"]},
    {"TO":["lydia_liu@awotglobal.com"],"CC":["jiangyaping@eoulu.com","zhaona@eoulu.com","zhaowenzhen@eoulu.com"]},
    {"TO":[""],"CC":["jiangyaping@eoulu.com","zhaona@eoulu.com","zhaowenzhen@eoulu.com"]}];
    
    // 报价系统PO Cascade PO配件 FORWARDER 和 ship to 信息映射
    var globalQuoCasPOPartForwarder = {
        "FEDEX998830478":[],
        "DHL952364959":[],
        "VOC Logistics-AMS":[
            {"Addr":"Fokkerweg 300 Building 7 1438 AN, Oude Meer The Netherlands"},
            {"Attn":"Wanfeng Liu"},
            {"Tel":"+31 615662431"}
        ],
        "ATLANTIC INTEGRATED FREIGHT GMBH":[
            {"Addr":"GEB 567A CARGO CITY SUED D-60549 FRANKFURT, GERMANY"},
            {"Tel":"+49 69 697 697 49"}
        ],
        "Kroll Internationale Spedition GmbH":[
            {"Addr":"Branch Dresden- DRS Cargo Building Wilhelmine-Reichard- Ring 3 D-01109 Dresden"},
            {"Attn":"Sven Nucklich"},
            {"Tel":"+49(0)351-881-4532"}
        ],
        "Hi Mat Express":[
            {"Addr":"163 East Compton Blvd Gardena, Ca 90248"},
            {"Attn":"John Jow"},
            {"Tel":"310-673-5888"}
        ]
    };
    var globalQuoCasPOPartShipTo = {
        "HK EOULU TRADING LIMITED":{
            "Company":"HK EOULU TRADING LIMITED",
            "Addr":"Room 1501, Grand Millennium Plaza (Lower Block), 181 Queen's Road Central, HONG KONG",
            "Tel":"00852-21527388",
            "Attn":"Nancy"
        },
        "Awot Global Express (HK) Ltd":{
            "Company":"Awot Global Express (HK) Ltd",
            "Addr":"Unit 256,Airport Freight Forwarding Centre,2 Chun Wan Road,Chek Lap Kok,Hong Kong",
            "Tel":"00852-22443069",
            "Attn":"HANG"
        },
        "Shing Fat Logistics Ltd.":{
            "Company":"Shing Fat Logistics Ltd.",
            "Addr":"A Room 23/F., Goodman Dynamic Centre, 188 Yeung Uk Road, Tsuen Wan, HONGKONG, CHINA",
            "Tel":"00852-25962121",
            "Attn":"Max Li"
        },
    };


/*****************************全局数据处理方法********************************/
    
//     var a = NaN;
//     var b= '222';
//     var c = null; 
//     var d = false;
//     var e = undefined;
//     var f = Symbol();
//     var arr = ['aa','bb','cc'];
//     var obj = {
//         'a': 'aa',
//         'b': 'bb',
//         'c': 'cc'
//     };
//     var res = Object.prototype.toString.call(arr);
//     console.log(res);   //[object Array]
//     var res2 = Object.prototype.toString.call(obj);
//     console.log(res2);   //[object Object]
//     var res3 = Object.prototype.toString.call(a);
//     console.log(res3);   //[object Number]
//     var res4 = Object.prototype.toString.call(b);
//     console.log(res4);   //[object String]
//     var res4 = Object.prototype.toString.call(c);
//     console.log(res4);   //[object Null]
//     var res5 = Object.prototype.toString.call(d);
//     console.log(res5);   //[object Boolean]
//     var res6 = Object.prototype.toString.call(e);
//     console.log(res6);   //[object Undefined]
//     var res7 = Object.prototype.toString.call(f);
//     console.log(res7);   //[object Symbol]


    // json对象字符串互转（调用方法 $.fn.stringifyArr(aa);）
    $.fn.stringifyArr = function(array){
        return JSON.stringify(array);
    };
    $.fn.parseArr = function(array){
        return JSON.parse(array);
    };

    // json对象转字符串（调用方法 $(bb).stringify();）（返回格式：{"0":{"a":1},"1":{"a":3},"length":2}）
    $.fn.stringify = function(){
        return JSON.stringify(this);
    };

    // 返回JSON的key+字符+value
    function globalJSONKeyVal(JSONobj,joinChar,splitChar){
        var str="";
        if(splitChar==null || splitChar==undefined){
            for(var k in JSONobj){
                str+=k+joinChar+JSONobj[k];
            }
        }else{
            for(var kk in JSONobj){
                str+=kk+joinChar+JSONobj[kk]+splitChar;
            }
        }
        return str;
    }

    // 百分数转小数
    function globalToPoint(percent){
        var str=percent.replace("%","");
        str= str/100;
        return str;
    }

    // 小数转百分数
    // 这里需要先用Number进行数据类型转换，然后去指定截取转换后的小数点后几位(按照四舍五入)，这里是截取一位，0.1266转换后会变成12.7%
    
    function globalToPercent(point,n){
        if(Number.isInteger(n)){
            var str=Number(point*100).toFixed(n);
            str+="%";
            return str;
        }else{
            console.log("globalToPercent函数参数错误");
        }
        
    }

    // 判断是否是整数
    function globalIsInteger1(obj) {
        return typeof obj === 'number' && obj%1 === 0;
    }
    function globalIsInteger2(obj) {
        return Math.floor(obj) === obj;
    }
    // 有一个缺点,globalIsInteger3(1000000000000000000000) // false
    function globalIsInteger3(obj) {
        return parseInt(obj, 10) === obj;
    }
    // 有个缺陷，位运算只能处理32位以内的数字，对于超过32位的无能为力，如globalIsInteger4(Math.pow(2, 32)) // 32位以上的数字返回false了
    function globalIsInteger4(obj) {
        return (obj | 0) === obj;
    }
    // ES6提供了Number.isInteger
    // Number.isInteger(3) // true
    // Number.isInteger(3.1) // false
    // Number.isInteger('') // false
    // Number.isInteger('3') // false
    // Number.isInteger(true) // false
    // Number.isInteger([]) // false
    // 目前，最新的Firefox和Chrome已经支持。
    // 
    //数据处理，对""、"--"、null、undefined处理
    function globalDataHandle(originData,outData){
        if(originData === "" || originData === "--" || originData === null || originData === undefined){
            originData=outData;
        }else{
            originData=originData;
        }
        return originData;
    }
    function globalDateDataHandle(originData,outData){
        if(originData === "" || originData === "--" || originData === "0000-00-00" || originData === null || originData === undefined){
            originData=outData;
        }else{
            originData=originData;
        }
        return originData;
    }

    // 判断对象是否是字符串
    function globalIsString(obj){ 
        return Object.prototype.toString.call(obj) === "[object String]";  
    } 

    // 判断对象是否是数组
    function globalIsArray(o){
        return Object.prototype.toString.call(o) === '[object Array]';
    }

    // 判断变量是否是对象
    function globalIsTrueObj(o){
        return Object.prototype.toString.call(o) === '[object Object]';
    }

    // 判断变量是否是[object FormData]对象
    function globalIsFormData(o){
        return Object.prototype.toString.call(o) === '[object FormData]';
    }

    // 判断变量是否是函数
    function globalIsFunction(obj) {
        return Object.prototype.toString.call(obj) === '[object Function]';
    }

    // 全局去除字符串的全部空格
    function globalTrim(str){
        var isStr = globalIsString(str);
        if(isStr){
            str = str.replace(/\s/g,'') ;
            return str;
        }else{
            console.log("globalTrim函数参数不是字符串");
        }
    }

    // 判断字符串是否都是数字
    function globalIsNumber(val) {
        var iFlag = globalIsString(val);
        if(iFlag){
            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
            if(regPos.test(val) || regNeg.test(val)) {
                return true;
            } else {
                return false;
            }
        }else{
            console.warn("globalIsNumber函数参数不是字符串");
        } 
    }

    //js统计字符串中包含的特定字符个数
    /*ECMAScript v3 规定，replace() 方法的参数 replacement 可以是函数而不是字符串。在这种情况下，每个匹配都调用该函数，它返回的字符串将作为替换文本使用。该函数的第一个参数是匹配模式的字符串。接下来的参数是与模式中的子表达式匹配的字符串，可以有 0 个或多个这样的参数。接下来的参数是一个整数，声明了匹配在 stringObject 中出现的位置。最后一个参数是 stringObject 本身。*/
    function globalPlaceholderCount(strSource) {
      //统计字符串中包含{}或{xxXX}的个数
      var thisCount = 0;
      /* /\{[xX]+\}|\{\}/g */
      strSource.replace(/\n/g, function (m, i) {
        //m为找到的{xx}元素、i为索引
        thisCount++;
      });
      return thisCount;
    }

    function globalStrHandleCount(reg,strSource) {
      //统计字符串中包含{}或{xxXX}的个数
      var thisCount = 0;
      /* /\{[xX]+\}|\{\}/g */
      strSource.replace(reg, function (m, i) {
        //m为找到的{xx}元素、i为索引
        thisCount++;
      });
      return thisCount;
    }

    // 数组去重、字符串去重
    function globalArrStrUnique(item){
        var arrFlag = globalIsArray(item);
        var strFlag = globalIsString(item);
        if(arrFlag || strFlag){
            var newArrStr = Array.from(new Set(item));
            return newArrStr;
        }else{
            console.log("globalArrStrUnique函数参数非法");
        }
    }

    // 统计数组中各项出现次数
    // const json = { name: '小缘', age: 14 }
    // Object.keys(json).forEach(key => {
    //   console.info(key + ':', json[key])
    // })
    function globalGetArrCounts(arra){
        var flag = globalIsArray(arra);
        if(flag){
            var counts = {};
            arra.forEach(function(item) { 
              counts[item] = (counts[item] || 0) + 1; 
            });
            return counts;
        }else{
            console.log("globalGetArrCounts参数不是数组");
        }
    }

    // 升序方法
    var ascCompare = function (x, y) {//比较函数
        if (x < y) {
            return -1;
        } else if (x > y) {
            return 1;
        } else {
            return 0;
        }
    };
    // 降序方法
    var descCompare = function (x, y) {
        if (x < y) {
            return 1;
        } else if (x > y) {
            return -1;
        } else {
            return 0;
        }
    };

    // 全局获取当日时间
    // flag为true时，返回年月日形式
    function globalGetToday(flag) {
        var date = new Date();
        var year = date.getFullYear();
        var moonth = date.getMonth()+1;
        var day = date.getDate();
        if(moonth<10){
            moonth = "0"+moonth;
        }
        if(day<10){
            day = "0"+day;
        }
        var GetToday;
        if(flag){
            GetToday = year+"年"+moonth+"月"+day+"日";
        }else{
            GetToday = year+"-"+moonth+"-"+day;
        }
        return GetToday;
    }

    // 给定时间差计算日期
    function globalCalcDate(date,num){
        var  oDate1  =  new  Date(date);
        var  intValue  =  num  *  (24  *  3600  *  1000) + oDate1.getTime();  
        var  endDate  =  new  Date  (intValue); 
        var endDate1 = endDate.getFullYear()+"-"+ ((endDate.getMonth()+1)<10?"0"+(endDate.getMonth()+1) : (endDate.getMonth()+1))+"-"+ (endDate.getDate()<10?"0"+endDate.getDate():endDate.getDate());
        return endDate1;
    }

    // 计算时间差
    function globalCalcTimeDiff(startDay,endDay,noAbsFlag){
        if(startDay!="" && endDay!=""){
            var startTime = new Date(startDay);
            var endTime = new Date(endDay);
            if(noAbsFlag){
                return ((startTime - endTime)/(1000*60*60*24))+"天";
            }
            return Math.abs((startTime - endTime)/(1000*60*60*24))+"天";
        }
    }

    // 给定毫秒数获取年月日
    function getYMDByMSEL(MSEL){
        var YMDStr = new Date(MSEL);
        var year = YMDStr.getFullYear();
        var moonth = YMDStr.getMonth()+1;
        var day = YMDStr.getDate();
        if(moonth<10){
            moonth = "0"+moonth;
        }
        if(day<10){
            day = "0"+day;
        }
        var YMD = year+"-"+moonth+"-"+day;
        return YMD;
    }

    // 中文日期转英文日期
    function globalDataCNToEN(str){
        var newStr = str.replace(/-/g,"");
        if(!isNaN(newStr)){
            var iArr = str.split("-");
            iArr.reverse();
            var ENstr = iArr.reduce(function(previousValue, currentValue){
                return previousValue + "/" + currentValue;
            });
            return ENstr;
        }else{
            console.log("globalDataCNToEN函数参数错误");
        }
    }

    // 中文日期转英文日期（mm/dd/yyyy）
    function globalDataCNToENMonth(str){
        var newStr = str.replace(/-/g,"");
        if(!isNaN(newStr) && globalStrHandleCount(/-/g,str)==2){
            var iArr = str.split("-");
            iArr[2] = iArr.splice(1,1,iArr[2])[0];
            iArr.reverse();
            var ENstr = iArr.reduce(function(previousValue, currentValue){
                return previousValue + "/" + currentValue;
            });
            return ENstr;
        }else{
            console.log("globalDataCNToENMonth函数参数错误");
        }
    }

    // 英文日期转中文日期
    function globalDataENToCN(str){
        var newStr = str.replace(/\//g,"");
        if(!isNaN(newStr)){
            var iArr = str.split("\/");
            iArr.reverse();
            var CNstr = iArr.reduce(function(previousValue, currentValue){
                return previousValue + "-" + currentValue;
            });
            return CNstr;
        }else{
            console.log("globalDataENToCN函数参数错误");
        }
    }

    // 表格里日期处理，自定义输出，需要预先设置td css的color #fff或者opacity:0
    function tdDateHandle(item,outData,font_color){
        $(item).each(function(){
            var curDate = $(this).text();
            var newCurDate = globalDateDataHandle(curDate,outData);
            $(this).text(newCurDate);
        });
        var that = $(item);
        setTimeout(function(){
            that.css({
                "color":font_color,
                "opacity":"1"
            });
        },50);
    }

    // 构造select里年的option字符串
    function globalBuildYOptionStr(startY,jQObj){
        var curY = new Date().getFullYear();
        var str1 = '<option value="" disabled>请选择</option>';
        do{
            str1 += '<option value="'+startY+'">'+startY+'年</option>';
            startY++;
        }while (startY < curY+1);
        jQObj.empty().append(str1);
    }

    // 构造select里月的option字符串
    function globalBuildMOptionStr(jQObj){
        var iii = 1;
        var str2 = '<option value="" disabled>请选择</option>';
        do{
            if(iii<10){
                iii = "0"+iii;
            }
            str2 = '<option value="'+iii+'">'+iii+'月</option>';
            iii = Number(iii);
            iii++;
        }while (iii < 13);
        jQObj.empty().append(str2);
    }

/*****************************全局弹窗按钮类*******************************/
    // 弹出框自适应
    function alertResponse(item,itemHeight,itemWidth) {
        // if ($(window).height() < itemHeight){
        //     $(item).css("top")
        // }
        var iH = Math.abs($(window).height() - itemHeight)/2;
        var iW = Math.abs($(window).width() - itemWidth)/2;
        $(item).css({"top":iH+"px","left":iW+"px"});
    }

    // 百分比弹出框(top固定)
    function percentWAlertResponse(item,num){
        var curW = $(item).width();
        if($(window).width() - curW < num){
            var leftPx = parseInt(num/2);
            $(item).css("left",leftPx+"px");
        }else{
            var leftPx2 = parseInt(($(window).width() - curW)/2);
            $(item).css("left",leftPx2+"px");
        }
    }

    // 仅left的弹窗response
    function leftAlertResp(item){
        var wid = parseFloat($(item).css("width"));
        var l = Math.abs($(window).width() - wid)/2;
        $(item).css("left",l+"px");
    }

    // 幕布与信息框
    function alertGroupOpen(bgItem,Item,speed) {
        $(bgItem).fadeIn(speed);
        $(Item).fadeIn(speed);
    }
    function alertGroupClose(bgItem,Item,speed) {
        $(bgItem).fadeOut(speed);
        $(Item).fadeOut(speed);
    }
    function alertGroupDelayClose(bgItem,Item,speed) {
        $(bgItem).delay(400).fadeOut(speed);
        $(Item).delay(450).fadeOut(speed);
    }

    function btnCanClick(aa) {
        $(aa).prop("disabled",false).removeClass("eou-btn-disabled eou-btn-abled").addClass("eou-btn-abled");
    }

    function btnNotClick(aa) {
        $(aa).prop("disabled","disabled").removeClass("eou-btn-disabled eou-btn-abled").addClass("eou-btn-disabled");
    }
    
    // 导航一级菜单跳转自适应
    function globalNavResponse(item,subItem,defaultItem,defaultHref){
        if($(item).length){
            $(item).each(function(){
                if($(this).find(defaultItem).length){
                    // var newItem = item+">a";
                    var defaultItem_a = $(this).find(defaultItem).children("a").attr("href");
                    console.warn(defaultItem_a);
                    if(defaultItem_a.indexOf(defaultHref)>-1){
                        $(this).children("a").attr("href",defaultHref);
                    }else{
                        $(this).children("a").attr("href",defaultItem_a);
                    }
                }else if($(this).find(subItem).length){
                    var responseHref = $(this).find(subItem).eq(0).children("a").attr("href");
                    // alert(responseHref);
                    $(this).children("a").attr("href",responseHref);
                }
            });
        }
    }

    // 导航二级菜单跳转自适应
    function globalSubnavResponse(item,subItem,defaultItem,defaultHref) {
        if($(item).length){
            $(item).each(function(){
                if($(this).find(defaultItem).length){
                    $(this).children("h5").children("a").attr("href",defaultHref);
                }else if($(this).find(subItem).length){
                    var responseHref = $(this).find(subItem).eq(0).children("a").attr("href");
                    // alert(responseHref);
                    $(this).children("h5").children("a").attr("href",responseHref);
                }
            });
        }
    }


/***************************全局邮件处理方法*****************************/
    // 全局收件人、抄送人添加删除
    function globalToCCAdd(dbsupItem,supItem,item,clickItem,sumNum,perRowNum,insertRowStr,textItem,textIn){
        var temp = 0;
        $(item).each(function(){
            if($(this).css("visibility")=="visible"){
                temp++;
            }
        });
        var supItemLen = $(supItem).length;
        if(temp/perRowNum==supItemLen){
            $(dbsupItem).append(insertRowStr);
            $(item+":lt("+temp+")").each(function(){
                $(this).find(clickItem).fadeOut(100);
            });
        }else{
            $(item).eq(temp-1).next().css({"visibility":"visible","opacity":"1"});
            $(item).eq(temp-1).next().find(clickItem).fadeIn(100);
            $(item+":lt("+temp+")").each(function(){
                $(this).find(clickItem).fadeOut(100);
            });
            if(temp==sumNum-1){
                $(dbsupItem).find(clickItem).fadeOut(100);
            }
        }
        for(var i = 1;i<temp+1;i++){
            $(item).eq(i).find(textItem).text(textIn+i);
        }
    }
    function globalToCCDel(supItem,item,perRowNum,delVal,textItem,textIn){
        var temp = 0;
        $(item).each(function(){
            if($(this).css("visibility")=="visible"){
                temp++;
            }
        });
        var valueArr = [];
        for(var j=0;j<temp;j++){
            valueArr.push($(item).eq(j).find("input[type='text']").val());
        }
        var delIndex = valueArr.indexOf(delVal);
        valueArr.splice(delIndex,1);
        console.log("valueArr:");
        console.log(valueArr);
        var temp2 = temp-1;
        var supItemLen = $(supItem).length;
        if(temp2/perRowNum==supItemLen-1){
            $(supItem).eq(supItemLen-1).remove();
            for(var jj=0;jj<valueArr.length;jj++){
                $(item).eq(jj).find("input[type='text']").val(valueArr[jj]);
            }
            $(item).eq(valueArr.length-1).find("span.i-add").fadeIn(100);
        }else{
            $(item).eq(temp-1).css({"visibility":"hidden","opacity":"0"});
            $(item).eq(temp-1).find("input").val("");
            for(var jjj=0;jjj<valueArr.length;jjj++){
                $(item).eq(jjj).find("input[type='text']").val(valueArr[jjj]);
            }
            $(item).eq(valueArr.length-1).find("span.i-add").fadeIn(100);
        }
        for(var i = 1;i<temp-1;i++){
            $(item).eq(i).find(textItem).text(textIn+i);
        }
    }


/************************全局表单元素处理方法****************************/
    // 全局响应式textarea
    function globalRespTextarea(TextareaArr,perColWid){
        var isArrayVar = globalIsArray(TextareaArr);
        if(isArrayVar){
            var curW = $(window).width();
            var newPerColWid = perColWid == undefined?11.68:perColWid;
            var colS = Math.round(curW/newPerColWid);
            TextareaArr.map(function(currentValue,index,arr){
                $(currentValue).attr("cols",colS);
            });
        }else{
            console.log("globalRespTextarea函数参数错误");
        }  
    }

    // 全局按钮等在请求时禁用
    function globalBtnNotAllow(jQObj){
        jQObj.css("cursor","not-allowed").prop("disabled","disabled");
    }

    // 全局按钮等在请求后可用
    function globalBtnAllow(jQObj){
        jQObj.css("cursor","pointer").prop("disabled",false);
    }


/*************************全局计算类函数**********************************/
    // 计算表格的合计，tfoot，把需要计算的数据放在tbody
    function globalCalcTABColSum(item){
        $(item).find("tfoot tr td:not(:nth-child(1))").each(function(){
            var index = $(this).index();
            var sum = 0;
            $(item).find("tbody tr").each(function(){
                sum+=($(this).find("td:eq("+index+")").text())*1;
            });
            $(this).text(sum);
        });
    }


    // 表格排序
    // HTML 结构如下
        // <table id="tableSort">
        //     <thead>
        //         <tr>
        //             <th type="string">
        //                 出版时间
        //             </th>
        //             <th type="number">
        //                 印刷量（册）
        //             </th>
        //             <th type="ip">
        //                 IP
        //             </th>
        //         </tr>
        //     </thead>
        //     <tbody>
        //         <tr class="hover">
        //             <td>
        //                 2009-10
        //             </td>
        //             <td>
        //                 50000
        //             </td>
        //             <td>
        //                 192.168.1.125
        //             </td>
        //         </tr>
        //         <tr>
        //             <td>
        //                 2009-09
        //             </td>
        //             <td>
        //                 800
        //             </td>
        //             <td>
        //                 192.68.1.125
        //             </td>
        //         </tr>
        //     </tbody>
        // </table>
        // @param 
        // sortIndex  由外部逻辑控制，如果已排序过，则传值与index相等
        // tbodyTr  tbody下的tr jquery对象
        // type  排序比较时的类型
        // index  td的索引值
        // sortFlag  ASC DESC 默认是升序
        // tbodySelector
        // fn  可在这里写逻辑控制sortIndex
    function globalSortTable(sortIndex, tbodyTr, type, index, sortFlag, tbodySelector, fn){
        //对表格排序
        var trsValue = new Array();
        tbodyTr.each(function () {
            var tds = $(this).find('td');
            //获取行号为index列的某一行的单元格内容与该单元格所在行的行内容添加到数组trsValue中
            trsValue.push(type + ".globalseparator" + $(tds[index]).html() + ".globalseparator" + $(this).html());
            $(this).html("");
        });
        var len = trsValue.length;
        if (index == sortIndex) {
        //如果已经排序了则直接倒序
            trsValue.reverse();
        } else {
            for (var i = 0; i < len; i++) {
                //split() 方法用于把一个字符串分割成字符串数组
                //获取每行分割后数组的第一个值,即此列的数组类型,定义了字符串\数字\Ip
                type = trsValue[i].split(".globalseparator")[0];
                for (var j = i + 1; j < len; j++) {
                    //获取每行分割后数组的第二个值,即文本值
                    value1 = trsValue[i].split(".globalseparator")[1];
                    //获取下一行分割后数组的第二个值,即文本值
                    value2 = trsValue[j].split(".globalseparator")[1];
                    //接下来是数字\字符串等的比较
                    if (type == "number") {
                        value1 = value1 == "" ? 0 : value1;
                        value2 = value2 == "" ? 0 : value2;
                        if(sortFlag.toUpperCase() == "ASC"){
                            if (parseFloat(value1) > parseFloat(value2)) {
                                var temp = trsValue[j];
                                trsValue[j] = trsValue[i];
                                trsValue[i] = temp;
                            }
                        }else if(sortFlag.toUpperCase() == "DESC"){
                            if (parseFloat(value1) < parseFloat(value2)) {
                                var temp = trsValue[j];
                                trsValue[j] = trsValue[i];
                                trsValue[i] = temp;
                            }
                        }
                        
                    } else if (type == "ip") {
                        if(sortFlag.toUpperCase() == "ASC"){
                            if (globalIp2Int(value1) > globalIp2Int(value2)) {
                                var temp = trsValue[j];
                                trsValue[j] = trsValue[i];
                                trsValue[i] = temp;
                            }
                        }else if(sortFlag.toUpperCase() == "DESC"){
                            if (globalIp2Int(value1) < globalIp2Int(value2)) {
                                var temp = trsValue[j];
                                trsValue[j] = trsValue[i];
                                trsValue[i] = temp;
                            }
                        }
                        
                    } else {
                        if(sortFlag.toUpperCase() == "ASC"){
                            if (value1.localeCompare(value2) > 0) {//该方法不兼容谷歌浏览器
                                var temp = trsValue[j];
                                trsValue[j] = trsValue[i];
                                trsValue[i] = temp;
                            }
                        }else if(sortFlag.toUpperCase() == "DESC"){
                            if (value1.localeCompare(value2) < 0) {//该方法不兼容谷歌浏览器
                                var temp = trsValue[j];
                                trsValue[j] = trsValue[i];
                                trsValue[i] = temp;
                            }
                        }
                        
                    }
                }
            }
        }

        for (var ii = 0; ii < len; ii++) {
            $(tbodySelector+" tr:eq(" + ii + ")").html(trsValue[ii].split(".globalseparator")[2]);
        }
        fn && fn();
    }

    // 全局IP转成整型
    function globalIp2Int(ip) {
        var num = 0;
        ip = ip.split(".");
        num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);
        return num;
    }

    /*
    
     //合同页面的阿拉伯数字转换为大写的汉字
         function changeMoneyToChinese(money){  
             var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字  
             var cnIntRadice = new Array("","拾","佰","仟"); //基本单位  
             var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位  
             var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位  
             var cnIntLast = "元"; //整型完以后的单位  
             var maxNum = 999999999999999.9999; //最大处理的数字  
             var IntegerNum; //金额整数部分  
             var DecimalNum; //金额小数部分  
             var ChineseStr=""; //输出的中文金额字符串  
             var parts; //分离金额后用的数组，预定义  
             if( money == "" ){  
                 return "";  
             }  
             money = parseFloat(money);  
             if( money >= maxNum ){  
                 $.alert('超出最大处理数字');  
                 return "";  
             }  
             if( money == 0 ){  
                 ChineseStr = cnNums[0]+cnIntLast  
                 return ChineseStr;  
             }  
             money = money.toString(); //转换为字符串  
             if( money.indexOf(".") == -1 ){  
                 IntegerNum = money;  
                 DecimalNum = '';  
             }else{  
                 parts = money.split(".");  
                 IntegerNum = parts[0];  
                 DecimalNum = parts[1].substr(0,4);  
             }  
             if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换  
                 zeroCount = 0;  
                 IntLen = IntegerNum.length;  
                 for( i=0;i<IntLen;i++ ){  
                     n = IntegerNum.substr(i,1);  
                     p = IntLen - i - 1;  
                     q = p / 4;  
                     m = p % 4;  
                     if( n == "0" ){  
                         zeroCount++;  
                     }else{  
                         if( zeroCount > 0 ){  
                             ChineseStr += cnNums[0];  
                         }  
                         zeroCount = 0; //归零  
                         ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];  
                     }  
                     if( m==0 && zeroCount<4 ){  
                         ChineseStr += cnIntUnits[q];  
                     }  
                 }  
                 ChineseStr += cnIntLast;  
             }  
             if( DecimalNum!= '' ){//小数部分  
                 decLen = DecimalNum.length;  
                 for( i=0; i<decLen; i++ ){  
                     n = DecimalNum.substr(i,1);  
                     if( n != '0' ){  
                         ChineseStr += cnNums[Number(n)]+cnDecUnits[i];  
                     }  
                 }  
             }  
             if( ChineseStr == '' ){  
                 ChineseStr += cnNums[0]+cnIntLast;  
             }  
             return ChineseStr;  
         }  
    //科学计数法
    var fmoney=function(s, n) {
        n = n > 0 && n <= 20 ? n : 2;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
        t = "";
        for (i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        return t.split("").reverse().join("") + "." + r;
    };
     
    //返回正常的数
     var rmoney=function(s) {  
        return parseFloat(s.replace(/[^\d\.-]/g, ""));  
    };

     */
    

/*****全局正则*****/
//日期的正则表达式
var regDate = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
//时间的正则表达式
var regTime = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;
// 字符串里含有正规日期格式即可
var regHasDate = /^.*[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]).*$/;
var regHasDateNoCTOR = /^.*[1-9]\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1]).*$/;
// 图片后缀名的正则
// new RegExp("(jpg|jpeg|gif|png)+","gi").test(oFile.type)
// 需要使用\.来匹配一个小数点，当然如果是在[]里面的话 是不需要加\的
var imgSuffix = /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/;
// 文件名非法字符正则
var fileIllegalChar = /[<>\/\\\|:""\*\?\.]/;
var fileReplaceIllegalChar = /[<>\/\\\|:""\*\?\.]+/g;
// var regDate1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate2 = /^[1-9]\d{3}-(0[1-9]|1[0-2])$/;
// var regDate3 = /^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate4 = /^[1-9]\d{3}$/;
// var regDate5 = /^(0[1-9]|1[0-2])$/;
// var regDate6 = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;

// 0-100数字
var reg0To100 = new RegExp("^(\\d|[1-9]\\d|100)$");
// 0-120数字
var reg0To120 = new RegExp("^(\\d|[1-9]\\d|[1][0-1]\\d|120)$");
// 全英文
var regAllEn = new RegExp("^[a-zA-Z]+$");
// 0-120含自定义
var reg0To120AddCus = new RegExp("^(\\d|[1-9]\\d|[1][0-1]\\d|120|p|P)$");
// var regIntOr1To2DeciPoi = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
// 非零开头的最多带两位小数的数字或0或0.1或0.53等
var regIntOr1To2DeciPoi = /^((([1-9][0-9]*)+(\.[0-9]{1,2})?)|0(\.[0-9]{1,2})?)$/;
// 前置序号，在开头，以.或、，再加上*个空格
var regPrepSerialNO = new RegExp("^[0-9壹贰叁肆伍陆柒捌玖拾零一二三四五六七八九十①②③④⑤⑥⑦⑧⑨⑩⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽❶❷❸❹❺❻❼❽❾❿]+[.、]+\u0020*");
// 前置序号，在开头，没有.或、，再加上*个空格，例如（1）(2)
var regPrepSerialNONotImmediate = new RegExp("^[(（][0-9]+[)）]\u0020*");

// 验证数字的正则表达式集 
// 验证数字：^[0-9]*$ 
// 验证n位的数字：^\d{n}$ 
// 验证至少n位数字：^\d{n,}$ 
// 验证m-n位的数字：^\d{m,n}$ 
// 验证零和非零开头的数字：^(0|[1-9][0-9]*)$ 
// 验证有两位小数的正实数：^[0-9]+(.[0-9]{2})?$ 
// 验证有1-3位小数的正实数：^[0-9]+(.[0-9]{1,3})?$ 
// 验证非零的正整数：^\+?[1-9][0-9]*$ 
// 验证非零的负整数：^\-[1-9][0-9]*$ 
// 验证非负整数（正整数 + 0） ^\d+$ 
// 验证非正整数（负整数 + 0） ^((-\d+)|(0+))$ 
// 验证长度为3的字符：^.{3}$ 
// 验证由26个英文字母组成的字符串：^[A-Za-z]+$ 
// 验证由26个大写英文字母组成的字符串：^[A-Z]+$ 
// 验证由26个小写英文字母组成的字符串：^[a-z]+$ 
// 验证由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$ 
// 验证由数字、26个英文字母或者下划线组成的字符串：^\w+$ 
// 验证用户密码:^[a-zA-Z]\w{5,17}$ 正确格式为：以字母开头，长度在6-18之间，只能包含字符、数字和下划线。 
// 验证是否含有 ^%&',;=?$\" 等字符：[^%&',;=?$\x22]+ 
// 验证汉字：^[\u4e00-\u9fa5],{0,}$ 
// 验证Email地址：/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/
// 验证InternetURL：^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$ ；^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$ 
// 验证电话号码：^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$：--正确格式为：XXXX-XXXXXXX，XXXX-XXXXXXXX，XXX-XXXXXXX，XXX-XXXXXXXX，XXXXXXX，XXXXXXXX。 
// 验证身份证号（15位或18位数字）：^\d{15}|\d{}18$ 
// 验证一年的12个月：^(0?[1-9]|1[0-2])$ 正确格式为：“01”-“09”和“1”“12” 
// 验证一个月的31天：^((0?[1-9])|((1|2)[0-9])|30|31)$ 正确格式为：01、09和1、31。 
// 整数：^-?\d+$ 
// 非负浮点数（正浮点数 + 0）：^\d+(\.\d+)?$ 
// 正浮点数 ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$ 
// 非正浮点数（负浮点数 + 0） ^((-\d+(\.\d+)?)|(0+(\.0+)?))$ 
// 负浮点数 ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$ 
// 浮点数 ^(-?\d+)(\.\d+)?$
// 

// 一、校验数字的表达式

// 1 数字：^[0-9]*$ 

// 2 n位的数字：^\d{n}$

// 3 至少n位的数字：^\d{n,}$ 

// 4 m-n位的数字：^\d{m,n}$ 

// 5 零和非零开头的数字：^(0|[1-9][0-9]*)$ 

// 6 非零开头的最多带两位小数的数字：^([1-9][0-9]*)+(.[0-9]{1,2})?$ 

// 7 带1-2位小数的正数或负数：^(\-)?\d+(\.\d{1,2})?$ 

// 8 正数、负数、和小数：^(\-|\+)?\d+(\.\d+)?$ 

// 9 有两位小数的正实数：^[0-9]+(.[0-9]{2})?$

// 10 有1~3位小数的正实数：^[0-9]+(.[0-9]{1,3})?$

// 11 非零的正整数：^[1-9]\d*$ 或 ^([1-9][0-9]*){1,3}$ 或 ^\+?[1-9][0-9]*$

// 12 非零的负整数：^\-[1-9][]0-9"*$ 或 ^-[1-9]\d*$

// 13 非负整数：^\d+$ 或 ^[1-9]\d*|0$

// 14 非正整数：^-[1-9]\d*|0$ 或 ^((-\d+)|(0+))$

// 15 非负浮点数：^\d+(\.\d+)?$ 或 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$

// 16 非正浮点数：^((-\d+(\.\d+)?)|(0+(\.0+)?))$ 或 ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$

// 17 正浮点数：^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$ 或 ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$

// 18 负浮点数：^-([1-9]\d*\.\d*|0\.\d*[1-9]\d*)$ 或 ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$

// 19 浮点数：^(-?\d+)(\.\d+)?$ 或 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$

// 二、校验字符的表达式

// 1 汉字：^[\u4e00-\u9fa5]{0,}$ 

// 2 英文和数字：^[A-Za-z0-9]+$ 或 ^[A-Za-z0-9]{4,40}$ 

// 3 长度为3-20的所有字符：^.{3,20}$ 

// 4 由26个英文字母组成的字符串：^[A-Za-z]+$ 

// 5 由26个大写英文字母组成的字符串：^[A-Z]+$ 

// 6 由26个小写英文字母组成的字符串：^[a-z]+$ 

// 7 由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$ 

// 8 由数字、26个英文字母或者下划线组成的字符串：^\w+$ 或 ^\w{3,20}$ 

// 9 中文、英文、数字包括下划线：^[\u4E00-\u9FA5A-Za-z0-9_]+$

// 10 中文、英文、数字但不包括下划线等符号：^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$

// 11 可以输入含有^%&',;=?$\"等字符：[^%&',;=?$\x22]+

// 12 禁止输入含有~的字符：[^~\x22]+

// 三、特殊需求表达式

// 1 Email地址：^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$ 

// 2 域名：[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.? 

// 3 InternetURL：[a-zA-z]+://[^\s]* 或 ^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$ 

// 4 手机号码：^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$ 

// 5 电话号码("XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX)：^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$  

// 6 国内电话号码(0511-4405222、021-87888822)：\d{3}-\d{8}|\d{4}-\d{7} 

// 7 身份证号(15位、18位数字)：^\d{15}|\d{18}$ 

// 8 短身份证号码(数字、字母x结尾)：^([0-9]){7,18}(x|X)?$ 或 ^\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$ 

// 9 帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$

// 10 密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)：^[a-zA-Z]\w{5,17}$

// 11 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)：^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$

// 12 日期格式：^\d{4}-\d{1,2}-\d{1,2}

// 13 一年的12个月(01～09和1～12)：^(0?[1-9]|1[0-2])$

// 14 一个月的31天(01～09和1～31)：^((0?[1-9])|((1|2)[0-9])|30|31)$

// 15 钱的输入格式：

// 16 1.有四种钱的表示形式我们可以接受:"10000.00" 和 "10,000.00", 和没有 "分" 的 "10000" 和 "10,000"：^[1-9][0-9]*$

// 17 2.这表示任意一个不以0开头的数字,但是,这也意味着一个字符"0"不通过,所以我们采用下面的形式：^(0|[1-9][0-9]*)$

// 18 3.一个0或者一个不以0开头的数字.我们还可以允许开头有一个负号：^(0|-?[1-9][0-9]*)$

// 19 4.这表示一个0或者一个可能为负的开头不为0的数字.让用户以0开头好了.把负号的也去掉,因为钱总不能是负的吧.下面我们要加的是说明可能的小数部分：^[0-9]+(.[0-9]+)?$

// 20 5.必须说明的是,小数点后面至少应该有1位数,所以"10."是不通过的,但是 "10" 和 "10.2" 是通过的：^[0-9]+(.[0-9]{2})?$

// 21 6.这样我们规定小数点后面必须有两位,如果你认为太苛刻了,可以这样：^[0-9]+(.[0-9]{1,2})?$

// 22 7.这样就允许用户只写一位小数.下面我们该考虑数字中的逗号了,我们可以这样：^[0-9]{1,3}(,[0-9]{3})*(.[0-9]{1,2})?$

// 23 8.1到3个数字,后面跟着任意个 逗号+3个数字,逗号成为可选,而不是必须：^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$

// 24 备注：这就是最终结果了,别忘了"+"可以用"*"替代如果你觉得空字符串也可以接受的话(奇怪,为什么?)最后,别忘了在用函数时去掉去掉那个反斜杠,一般的错误都在这里

// 25 xml文件：^([a-zA-Z]+-?)+[a-zA-Z0-9]+\\.[x|X][m|M][l|L]$

// 26 中文字符的正则表达式：[\u4e00-\u9fa5]

// 27 双字节字符：[^\x00-\xff] (包括汉字在内，可以用来计算字符串的长度(一个双字节字符长度计2，ASCII字符计1))

// 28 空白行的正则表达式：\n\s*\r (可以用来删除空白行)

// 29 HTML标记的正则表达式：<(\S*?)[^>]*>.*?</\1>|<.*? /> (网上流传的版本太糟糕，上面这个也仅仅能部分，对于复杂的嵌套标记依旧无能为力)

// 30 首尾空白字符的正则表达式：^\s*|\s*$或(^\s*)|(\s*$) (可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式)

// 31 腾讯QQ号：[1-9][0-9]{4,} (腾讯QQ号从10000开始)

// 32 中国邮政编码：[1-9]\d{5}(?!\d) (中国邮政编码为6位数字) 33 IP地址：\d+\.\d+\.\d+\.\d+ (提取IP地址时有用) 34 IP地址：((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)) 


/*****全局ajax方法*****/
    // 惰性加载，包含搜索和不搜索的页面，仅供展示
    function globalInertLoadNoSearch(immediate, immediateFn, dataObj, searchParam, curPage, expireFalseFn, expireTrueFn, pageNotExistFn, searchParamNotExistFn){
        var curTime = new Date().getTime();
        /*如果是添加修改、更改状态类，需要执行此通道*/
        /*立即执行更新数据并渲染*/
        if(immediate){
            console.log("立即执行更新数据并渲染");
            immediateFn && immediateFn(curTime);
            return false;
        }
        console.log("不立即执行更新数据");
        if(!globalIsTrueObj(dataObj)){
            dataObj = {};
        }
        /*Object.getOwnPropertyNames()  返回对象的所有自身属性的属性名（包括不可枚举的属性）组成的数组，但不会获取原型链上的属性*/
        /*Object.keys()用于获取对象自身所有的可枚举的属性值，但不包括原型中的属性，然后返回一个由属性名组成的数组。注意它同for..in一样不能保证属性按对象原来的顺序输出*/
        /*存在该搜索param*/
        if(Object.getOwnPropertyNames(dataObj).indexOf(searchParam)>-1){
            console.log("存在该搜索param");
            /*存在该页数*/
            var searchParamVal = dataObj[searchParam];
            if(Object.getOwnPropertyNames(searchParamVal).indexOf(curPage.toString())>-1){
                console.log("存在该搜索param，存在该页数");
                // /*搜索内容一致*/
                // if(globalCompareVariable(searchParamVal[curPage].searchObj, searchObj)){
                //     console.log("存在该搜索param，存在该页数，搜索内容一致");
                // }
                // /*搜索内容不一致*/
                // else{
                //     console.log("存在该搜索param，存在该页数，搜索内容不一致");
                // }
                // 
                /*未超期*/
                if(curTime <= searchParamVal[curPage].expire){
                    console.log("存在该搜索param，存在该页数，未超期");
                    var resp = searchParamVal[curPage].resp;
                    expireFalseFn && expireFalseFn(resp);
                }
                /*已经超期*/
                else{
                    console.log("存在该搜索param，存在该页数，已经超期");
                    expireTrueFn && expireTrueFn(curTime);
                }
            }
            /*不存在该页数*/
            else{
                console.log("存在该搜索param，不存在该页数");
                pageNotExistFn && pageNotExistFn(curTime);
            }
        }
        /*不存在该搜索param*/
        else{
            console.log("不存在该搜索param");
            searchParamNotExistFn && searchParamNotExistFn(curTime);
        }
        

        // /*存在该页数*/
        // if(Object.getOwnPropertyNames(dataObj).indexOf(gCurPage.toString())>-1){
        //     console.log("存在该页数");
        //     /*搜索内容一致*/
        //     if(globalCompareVariable(dataObj[gCurPage].searchObj, searchObj)){
        //         console.log("存在该页数，搜索内容一致");
        //         /*未超期*/
        //         if(curTime <= dataObj[gCurPage].expire){
        //             console.log("存在该页数，搜索内容一致，未超期");
        //             var res = dataObj[gCurPage].res;
        //             expireFalseFn && expireFalseFn(res);
        //         }
        //         /*已经超期*/
        //         else{
        //             console.log("存在该页数，搜索内容一致，已经超期");
        //             expireTrueFn && expireTrueFn(curTime);
        //         }
        //     }
        //     /*搜索内容不一致*/
        //     else{
        //         console.log("存在该页数，搜索内容不一致");
        //         searchConDiffFn && searchConDiffFn(curTime);
        //     }
        // }
        // /*不存在该页数*/
        // else{
        //     console.log("不存在该页数");
        //     pageNotExistFn && pageNotExistFn(curTime);
        // }

    }
    

/*
* 全局判断两个变量是否相等
* 调用方法： globalCompareVariable(a, b) 或 globalCompareVariable(a, b, [], [])
* a 和 b 是函数时，开头的function关键字后面可以有空格，返回true，其他地方的空格不行
 */
function globalCompareVariable(a, b, aStack, bStack) {
    // === 结果为 true 的区别出 +0 和 -0
    if (a === b) return a !== 0 || 1 / a === 1 / b;

    // typeof null 的结果为 object ，这里做判断，是为了让有 null 的情况尽早退出函数
    if (a == null || b == null) return false;

    // 判断 NaN
    if (a !== a) return b !== b;

    // 判断参数 a 类型，如果是基本类型，在这里可以直接返回 false
    var type = typeof a;
    /*自己加的*/
    if(type === 'function' && typeof b === 'function'){
        var fnReg =  /^(function\s*)(\w*\b)/;
        return a.toString().replace(/^function\s*/,"function").replace(fnReg,'$1') === b.toString().replace(/^function\s*/,"function").replace(fnReg,'$1');
        // return a.toString().replace(fnReg,'$1') === b.toString().replace(fnReg,'$1');
    }
    /*自己加的结束*/
    if (type !== 'function' && type !== 'object' && typeof b != 'object') return false;

    // 更复杂的对象使用 globalCompareVariableDeep 函数进行深度比较
    return globalCompareVariableDeep(a, b, aStack, bStack);
}

function globalCompareVariableDeep(a, b, aStack, bStack) {
    // a 和 b 的内部属性 [[class]] 相同时 返回 true
    var className = Object.prototype.toString.call(a);
    if (className !== Object.prototype.toString.call(b)) return false;

    switch (className) {
        case '[object RegExp]':
        case '[object String]':
            return '' + a === '' + b;
        case '[object Number]':
            if (+a !== +a) return +b !== +b;
            return +a === 0 ? 1 / +a === 1 / b : +a === +b;
        case '[object Date]':
        case '[object Boolean]':
            return +a === +b;
    }

    var areArrays = className === '[object Array]';
    // 不是数组
    if (!areArrays) {
        // 过滤掉两个函数的情况
        if (typeof a != 'object' || typeof b != 'object') return false;

        var aCtor = a.constructor,
            bCtor = b.constructor;
        // aCtor 和 bCtor 必须都存在并且都不是 Object 构造函数的情况下，aCtor 不等于 bCtor， 那这两个对象就真的不相等啦
        if (aCtor !== bCtor && !(globalIsFunction(aCtor) && aCtor instanceof aCtor && globalIsFunction(bCtor) && bCtor instanceof bCtor) && ('constructor' in a && 'constructor' in b)) {
            return false;
        }
    }


    aStack = aStack || [];
    bStack = bStack || [];
    var length = aStack.length;

    // 检查是否有循环引用的部分
    while (length--) {
        if (aStack[length] === a) {
            return bStack[length] === b;
        }
    }

    aStack.push(a);
    bStack.push(b);

    // 数组判断
    if (areArrays) {
        length = a.length;
        if (length !== b.length) return false;

        while (length--) {
            if (!globalCompareVariable(a[length], b[length], aStack, bStack)) return false;
        }
    }
    // 对象判断
    else {
        var keys = Object.keys(a),
            key;
        length = keys.length;

        if (Object.keys(b).length !== length) return false;
        while (length--) {

            key = keys[length];
            if (!(b.hasOwnProperty(key) && globalCompareVariable(a[key], b[key], aStack, bStack))) return false;
        }
    }

    aStack.pop();
    bStack.pop();
    return true;
}


    // 全局获得员工所有信息
var globalGetStaffAllInfo = function(StaffName,item){
    $.ajax({
        type: 'GET',
        url: 'GetStaffInfo',
        data: {
            Type: "all",
            param: StaffName
        },
        dataType: "json"
    })
    .then(function(data){
        var str='';
        if(data.length>1){
            data.map(function(currentValue,index,arr){
                if(index>0){
                    str+='<option value="'+currentValue.StaffName+'" text="'+currentValue.ID+'" birthday="'+currentValue.Birthday+'" detailaddress="'+currentValue.DetailAddress+'" entrydate="'+currentValue.EntryDate+'" gender="'+currentValue.Gender+'" department="'+currentValue.Department+'" job="'+currentValue.Job+'" linktel="'+currentValue.LinkTel+'" quit="'+currentValue.Quit+'" staffmail="'+currentValue.StaffMail+'">'+currentValue.StaffName+'&nbsp;:&nbsp;'+currentValue.Department+'</option>';
                }
            });
            $(item).empty().append(str);
            $(item).fadeIn(100);
        }else{
            $(item).fadeOut(100);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，员工信息获取有误！");
    });
};

    // 全局通过部门获得员工所有信息
    // 返回信息格式
    // {
    //      ID: "10",
    //      StaffName: "高振鹏"
    // }
var globalGetStaffAllInfoByDepart = function(Department,fn,fn2){
    $.ajax({
        type: 'GET',
        url: 'GetStaffInfo',
        data: {
            Type: "common",
            Department: Department
        },
        dataType: "json"
    })
    .then(function(data, textStatus, jqXHR){
        console.log("处理完毕");
        fn && fn(data);
    },function(jqXHR, textStatus, errorThrown){
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，员工信息获取有误！");
    }).always(function(jqXHR, textStatus, jqXHR2){
        // if (textStatus != "success") {
        //     alert("Error: " + jqXHR.statusText); //error is always called .statusText
        // } else {
        //     alert("Success: " + jqXHR.response); //might not always be named .response
        // }
        // 
        // first variable is deformatted response form server, jqXHR2 is actual jqXHR object
        /*
            .always(function(a, textStatus, b) {
                if (textStatus == "success") {
                      var a = data, b = jqXHR;
                      alert("Success: " + data);
                } else {
                      var a = jqXHR, b = errorThrown;
                      alert("Error: " + errorThrown);
                }
                // initiate next sequential operation
            });
        */
        fn2 && fn2();
    });
};

    // 全局获取所有邮箱
    function globalGetAllEmail(fn, fn2){
        $.ajax({
            type: "GET",
            url: "GetAllEmail",
            data: {},
            dataType: "json"
        }).then(function(data){
            fn && fn(data);
        },function(){
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，邮箱信息获取有误！");
        }).always(function(){
            fn2 && fn2();
        });
    }


    //  获取所搜索型号的所有信息
    //  @param  classify ["型号","商品名称"]
    //  CommodityName 商品描述
var globalGetCommodityInfo = function(classify, param, item){
    $.ajax({
        type: 'GET',
        url: 'GetCommodityInfo',
        data: {
            Type: "singleSelect",
            classify: classify,
            param: param
        },
        dataType: "json"
    })
    .then(function(data){
        var str='';
        if(data.length>1){
            data.map(function(currentValue,index,arr){
                if(index>0){
                    str+='<option value="'+currentValue.Model+'" text="'+currentValue.ID+'" commodityname="'+currentValue.CommodityName+'" costprice="'+currentValue.CostPrice+'" deliveryperiod="'+currentValue.DeliveryPeriod+'" discountcost="'+currentValue.DiscountCost+'" producingarea="'+currentValue.ProducingArea+'" quotetime="'+currentValue.QuoteTime+'" sellerpricethree="'+currentValue.SellerPriceThree+'" sellerpricetwo="'+currentValue.SellerPriceTwo+'" supplier="'+currentValue.Supplier+'" unit="'+currentValue.Unit+'" unitprice="'+currentValue.UnitPrice+'">'+currentValue.Model+'&nbsp;:&nbsp;'+currentValue.CommodityName.substring(0,30)+'</option>';
                }
            });
            $(item).empty().append(str);
            $(item).fadeIn(100);
        }else{
            $(item).fadeOut(100);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，商品信息获取有误！");
    });
};

    //  获取所搜索型号的部分信息
    //  @param  param 搜索值
    //  CommodityName 商品描述
var globalGetCommoditySectionInfo = function(param, item){
    $.ajax({
        type: 'GET',
        url: 'GetCommodityModel',
        data: {
            Key: param
        },
        dataType: "json"
    })
    .then(function(data){
        var str='';
        if(data.length>1){
            data.map(function(currentValue,index,arr){
                if(index>0){
                    var iCommodityName = currentValue.CommodityName.replace(/\"/g,"");
                    str+='<option value="'+currentValue.Model+'" text="'+currentValue.ID+'" commodityname="'+iCommodityName+'" sellerpriceone="'+currentValue.SellerPriceOne+'">'+currentValue.Model+'&nbsp;:&nbsp;'+currentValue.CommodityName.substring(0,45)+'</option>';
                }
            });
            $(item).empty().append(str);
            $(item).fadeIn(100);
        }else{
            $(item).fadeOut(100);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，商品信息获取有误！");
    });
};


/*******************************全局image对象和canvas对象操作方法****************************************/
// 全局将file对象（blob）（图片文件流）转换成Dataurl字符串，并插入item中
var globalGetImageDataurl = function(file, quality, item, w, h){
    imageResizeTool.filetoDataURL(file,function(res){
        imageResizeTool.dataURLtoImage(res,function(imgObj){
            var canvasObj = imageResizeTool.imagetoCanvas(imgObj);
            var img_jpgData = imageResizeTool.canvasResizetoDataURL(canvasObj,quality);
            var img = document.createElement("img");
            img.onload = function (e) {
                window.URL.revokeObjectURL(img.src);
            };
            img.src = img_jpgData;
            if(!w){
                img.height = h;
            }
            if(!h){
                img.width = w;
            }
            $(item).empty().append(img);
        });
    });
};

// 全局自动下载图片
// that: img对象
var globalAutoDownloadImg = function(that, filename){
    var strData = that.attr("src").replace("application/x-msdownload","image/octet-stream");
    // var strData = $(this).attr("src").replace("image/jpeg","image/octet-stream");
    // document.location.href = strData;
    var a = document.createElement('a');
    a.href = strData;
    a.download = filename;
    a.click();
    $(a).remove();
};

// 全局下载文件（后台返回的文件流）
// @param data: 一个JSON对象（key/value 对象）
var globalAutoDownloadFile = function(methods, iurl, filename, data){
    var xhr = new XMLHttpRequest();
    xhr.open(methods, iurl, true);
    xhr.responseType = "blob";
    //xhr.setRequestHeader("client_type", "DESKTOP_WEB");
    //xhr.setRequestHeader("desktop_web_access_key", _desktop_web_access_key);
    xhr.onload = function () {
        if (this.status == 200) {
            var blob = this.response;
            var reader = new FileReader();
            reader.readAsDataURL(blob);  // 转换为base64，可以直接放入a表情href
            reader.onload = function (e) {
                // 转换完成，创建一个a标签用于下载
                var a = document.createElement('a');
                a.download = filename;
                a.href = e.target.result;
                $("body").append(a);  // 修复firefox中无法触发click
                a.click();
                $(a).remove();
            };
        }
    };
    if(methods=="POST"){
        // 格式 name=jack&age=18 字符串的格式
        // ajax.send('name=jack&age=998');
        // 可用 $.param 方法，传入一个JSON对象（key/value 对象）
        // 或者$("form").serialize()
        var paramData = $.param(data);
        xhr.send(paramData);
    }else{
        xhr.send();
    }
};

// 全局下载文件（直接请求接口）
// @param data: 一个JSON对象（key/value 对象）
var globalUrlDownloadFile = function(methods, iurl, filename, data){
    if(methods=="GET"){
        var a = document.createElement('a');
        if(filename){
            a.download = filename;
        }
        a.href = iurl;
        $("body").append(a);  // 修复firefox中无法触发click
        a.click();
        $(a).remove();
    }else if(methods=="POST"){
        var formStr = '<form method="'+methods+'" action="'+iurl+'">';
        $.each(data, function(key, value){
            formStr += '<input name="'+key+'" value="'+value+'">';
        });
        formStr += '</form>';
        var formObj = $(formStr);
        $("body").append(formObj);
        formObj.submit();
        formObj.remove();
    }
};

// 全局获取图片宽、高、大小
var globalGetImgWHSize = function(file, fn){
    // var f = document.getElementByIdx_x_x_x_x("tp").files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        var data = e.target.result;
        //加载图片获取图片真实宽度和高度
        var image = new Image();
        image.onload=function(){
            var width= image.width;
            var height= image.height;
            var fileSize= file.size;
            fn && fn(width, height, fileSize);
        };
        image.src= data;
    };
    reader.readAsDataURL(file);
};

// 全局上传文件
    // @param formData
        // var formData = new FormData();
        // formData.enctype="multipart/form-data";
        // formData.append("Year",Year);
        // // formData.append("ID",ID);
        // var fileList = BiddingDocumentState.uploadFileList;
        // $.each(fileList, function(iname, ivalue){
        //     formData.append("file",ivalue);
        // });
        // //formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
        // // formData.append("Operate","upload");
    // @param option
        // var option = {
        //     type: "POST",
        //     accept: "application/json; charset=utf-8",
        //     url: "SendToBusiness",
        //     dataType: "json"
        // };
    // @param progressFn
        // var newWidthFloat =  globalToPoint(percent);  
        // var newWidth = newWidthFloat*400;
        // console.log("进度条宽度："+newWidth);   
        // $(".progressIn").css("width",newWidth+"px");
        // $(".progressIn").text(percent);
    // @param beforeSendFn(XMLHttpRequest)
        // iThat.css("cursor","not-allowed").prop("disabled","disabled");
    // @param completeFn(XMLHttpRequest, textStatus)
        // if(textStatus=="success"){
        // }
        // iThat.css("cursor","pointer").prop("disabled",false);
function globalUploadFiles(formData, option, progressFn, successFn, errorFn, beforeSendFn, completeFn){
    // console.log(formData);
    // console.log(Object.prototype.toString.call(formData));                                           
    if(!globalIsFormData(formData)){
        formData = new FormData();
        formData.enctype="multipart/form-data";
    }
    if(!globalIsTrueObj(option)){
        option = {};
    }
    console.log(option);
    var type = option.type || "POST";
    var accept = option.accept || "text/html; charset=UTF-8";
    var URL = option.url || "";
    var dataType = option.dataType || "text";
    $.ajax({
        type: type,
        async: true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        // accept: 'text/html;charset=UTF-8',
        accept: accept,
        data: formData,
        // contentType:"multipart/form-data",
        url: URL,
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: dataType,
        xhr: function(){                        
            var globalMyXhr = $.ajaxSettings.xhr();
            if(globalMyXhr.upload){ // check if upload property exists
                globalMyXhr.upload.addEventListener('progress',function(e){                           
                    var loaded = e.loaded;                  //已经上传大小情况 
                    var total = e.total;                      //附件总大小 
                    var percent = Math.floor(100*loaded/total)+"%";     //已经上传的百分比  
                    console.log("已经上传了："+percent);
                    progressFn && progressFn();
                }, false); // for handling the progress of the upload
            }
            return globalMyXhr;
        },                    
        success: function(data){
            successFn && successFn(data);
        },
        error: function(){
            errorFn && errorFn();
        },
        beforeSend: function(XMLHttpRequest){
            beforeSendFn && beforeSendFn(XMLHttpRequest);
        },
        complete: function(XMLHttpRequest, textStatus){
            completeFn && completeFn(XMLHttpRequest, textStatus);
        }
    });                             
}

