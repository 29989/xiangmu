initMenuData();

function initMenuData() {
    //发送请求 获取当前登录用户的权限数据
    $.ajax({
        url:"/permissions/current",
        type:"POST",
        async:false,
        success:function (data) {
            var menu = $("#menu");
            
            $.each(data,function (indec, item) {
                //创建a
                var a = $("<a href='javascript:;'></a>");

                //拿到css
                var css = item.css;
                if (css!=null && css!=""){
                    a.append("<i aria-hidden='true' class='fa " + css +"'></i>");
                };
                a.append("<cite>"+item.name+"</cite>");
                a.attr("lay-id", item.id);
                var href = item.href;
                if(href != null && href != ""){
                    a.attr("data-url", href);
                };
                //创建li
                var li = $("<li class='layui-nav-item'></li>");
                //把a挂载到li上               l
                li.append(a);
                //把li挂在到ul上（menu）
                menu.append(li);

                //处理多级菜单
                setChild(li,item.child)

            });
            
        }
    })
}

function setChild(parentElement, child){
    if(child != null && child.length > 0){
        $.each(child,function (index, item) {
            var a = $("<a href='javascript:;'></a>");
            var css = item.css;
            if (css!=null && css!=""){
                a.append("<i aria-hidden='true' class='fa " + css +"'></i>");
            };
            a.append("<cite>"+item.name+"</cite>");
            a.attr("lay-id", item.id);
            var href = item.href;
            if(href != null && href != ""){
                a.attr("data-url", href);
            };

            var dd = $("<dd></dd>");
            dd.append(a);

            var dl = $("<dl class='layui-nav-child'></dl>");
            dl.append(dd);

            parentElement.append(dl);

            // 閫掑綊
            setChild(dd, item.child);

        })

    }

}
var active;

layui.use(['layer', 'element'], function() {
    var $ = layui.jquery,
        layer = layui.layer;
    var element = layui.element; //瀵艰埅鐨刪over鏁堟灉銆佷簩绾ц彍鍗曠瓑鍔熻兘锛岄渶瑕佷緷璧杄lement妯″潡
    element.on('nav(demo)', function(elem){
        //layer.msg(elem.text());
    });

    //瑙﹀彂浜嬩欢
    active = {
        tabAdd: function(obj){
            var lay_id = $(this).attr("lay-id");
            var title = $(this).html() + '<i class="layui-icon" data-id="' + lay_id + '"></i>';
            //鏂板涓€涓猅ab椤?
            element.tabAdd('admin-tab', {
                title: title,
                content: '<iframe src="' + $(this).attr('data-url') + '"></iframe>',
                id: lay_id
            });
            element.tabChange("admin-tab", lay_id);
        },
        tabDelete: function(lay_id){
            element.tabDelete("admin-tab", lay_id);
        },
        tabChange: function(lay_id){
            element.tabChange('admin-tab', lay_id);
        }
    };

    $(document).on('click','a',function(){
        if(!$(this)[0].hasAttribute('data-url') || $(this).attr('data-url')===''){
            return;
        }
        var tabs = $(".layui-tab-title").children();
        var lay_id = $(this).attr("lay-id");

        for(var i = 0; i < tabs.length; i++) {
            if($(tabs).eq(i).attr("lay-id") == lay_id) {
                active.tabChange(lay_id);
                return;
            }
        }
        active["tabAdd"].call(this);
        resize();
    });


    function resize(){
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function() {
            $(this).height($content.height());
        });
    }
    $(window).on('resize', function() {
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function() {
            $(this).height($content.height());
        });
    }).resize();


    $('.admin-side-toggle').on('click', function() {
        var sideWidth = $('#admin-side').width();
        if(sideWidth === 200) {
            $('#admin-body').animate({
                left: '0'
            });
            $('#admin-footer').animate({
                left: '0'
            });
            $('#admin-side').animate({
                width: '0'
            });
        } else {
            $('#admin-body').animate({
                left: '200px'
            });
            $('#admin-footer').animate({
                left: '200px'
            });
            $('#admin-side').animate({
                width: '200px'
            });
        }
    });


    var treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade');
    treeMobile.on('click', function () {
        $('body').addClass('site-mobile');
    });
    shadeMobile.on('click', function () {
        $('body').removeClass('site-mobile');
    });
});