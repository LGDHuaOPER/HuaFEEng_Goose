
var Cookie = {
    /**
     * 设置cookie
     * @param key cookie名
     * @param value cookie名
     * @param expires cookie有效时间 number型 如 输入7 表示有效期7天
     * @param path cookie路径
     * @param domin 域名
     * @param issecure  是否安全
     */
    setCookie : function (key,value,expires,path,domin,issecure){
        var str = encodeURIComponent(key) + "=" +encodeURIComponent(value) ;
        if(typeof expires == "number"){
            var date = new Date();
            date.setDate(date.getDate() + expires);
            str += ";expires=" + date;
        }
        if(path){
            str += ";path=" + path;
        }
        if(domin){
            str += ";domin=" + domin;
        }
        if(issecure){
            str += ";secure" ;
        }
        document.cookie = str ;
    },
    /**
     * 删除cookie
     * @param key
     */
    removeCookie : function (key){
        Cookie.setCookie(key,"",-1)
    },
    /**
     * 获取cookie
     * @param key
     * @returns {*}
     */
    getCookie : function (key){
        var str = document.cookie;
        var arr = str.split("; ");
        for( var i = 0; i < arr.length; i++ ){
            if(arr[i].indexOf(key) != -1){
                var temp = arr[i].split("=");
                if(temp[0] == key){
                    return decodeURIComponent(temp[1]);
                }
            }
        }
        return null;
    },
    /**
     * cookie存放多个时获取
     * @param key1
     * @param key2
     * @returns {*}
     */
    getCookieFromMore : function (key1,key2){
        var str =  Cookie.getCookie(key1);

        //当key1没传入是即str为空，则判断！str -- false  直接结束
        if(!str){
            return;
        }
        var arr = str.split("&");
        //[wangwang ,password=123 ,name=qqq]
        for(var i = 0;i < arr.length; i++){
            if(arr[i].indexOf(key2) != -1){
                var tempArr = arr[i].split("=");
                //[password ,123]
                if(tempArr[0] == key2){
                    return decodeURIComponent(tempArr[1])
                }
            }
        }
        return null;
    },
    /*
    * 存放多个
    * */
    setcookieAtMulti: function (key1,key2,value,expires){
        var val =  Cookie.getCookie(key1);
    // name = 苹果&num = 2&expire = 5;
    var subValueList = val.split("&");
    // [name = 苹果,num = 2,expire = 5];
    for(var i = 0 ; i < subValueList.length; i++){
        if(subValueList[i].indexOf(key2) != -1){
            var temp = subValueList[i].split("=");
            //[num , 2]
            if(temp[0] == key2){
                subValueList[i] = key2 + "=" + value ;
                val = subValueList.join("&");
                Cookie.setCookie(key1,val,expires||30);
                break;
            }
        }
    }
}
}







