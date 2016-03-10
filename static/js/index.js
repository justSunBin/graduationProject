$(function () {
	var img_index=0;
	var img_max=$("#slideshow-list li").length-1;
	//动态调整轮播图的width
	window.onload  = showImg;
	window.onresize = showImg;
	//轮播图片
	setInterval(function(){
		if (img_index==img_max) {
			img_index=0;
			$("#slideshow-list li:eq("+img_max+")").fadeOut(300,function(){
				$("#slideshow-list li:eq(0)").fadeIn(600);
			});
			// return true;
		}else{
			$("#slideshow-list li:eq("+img_index+")").fadeOut(300,function(){
				img_index++
				$("#slideshow-list li:eq("+img_index+")").fadeIn(600);
			});
			// img_index++;
		}
	},8000);
	// $('.single').pickmeup({
	// 	flat	: true
	// });
	// $('.multiple').pickmeup({
	// 	flat	: true,
	// 	mode	: 'multiple'
	// });
	// $('.range').pickmeup({
	// 	flat	: true,
	// 	mode	: 'range'
	// });
	// var plus_5_days	= new Date;
	// plus_5_days.addDays(5);
	// $('#checkout').pickmeup({
	// 	date		: [
	// 		new Date,
	// 		plus_5_days
	// 	],
	// 	mode		: 'range',
	// 	calendars	: 2,
	// 	format  : 'Y-m-d'
	// });

	//日历
	$('#checkout,#checkin').pickmeup({
		hide_on_select	: true,
		format  : 'Y-m-d'
	});
	//地址选择
	$("#location").bind("click",function (){
		getProvince();
	});
	$(document).bind("click",function(e){ 
		var target = $(e.target); 
		if(target.closest(".location-block").length == 0&&target.closest("#location").length == 0){ 
		$(".location-block").fadeOut();
		} 
	});
	//首行下拉
	$('#register').click(function(){
		$('#register-divsion').notifyModal({
		  duration : -1,
		  placement : 'centerTopSlide',
		  type : 'dark',
		  overlay : true
		});
		//绑定input特效
		$(".label_better").label_better({
			position: "right",
	     	easing: "ease-in-out",
	     	offset: -10
		});
	});
	$('#login').click(function(){
		$('#login-divsion').notifyModal({
		  duration : -1,
		  placement : 'center',
		  type : 'dark',
		  overlay : true
		});
		//绑定input特效
		$(".label_better").label_better({
			position: "right",
	     	easing: "ease-in-out",
	     	offset: -10
		});
	});
	$('#user').click(function(){
		$('#user').popModal({
			html : function(callback) {
			    $.ajax({
	                url:"/getHtml",
	                success:function(data) {
	                    callback(data);
					}
				})
			},
			placement : 'bottomRight',
			// onDocumentClickClosePrevent : '',
			// showCloseBut : false,
			overflowContent: true,
			// onDocumentClickClose : true,
			asMenu : false,
			inline : false,
			onOkBut : function(){},
			onCancelBut : function(){},
			onLoad : function(){},
			onClose : function(){},
			beforeLoadingContent : 'Please, waiting...'
		});
	});
})
	var address=new Array(3);
	function showImg()
	{
	    var w = document.body.clientWidth;	   
	    if (w < 1280){
	        $("#slideshow-list ul li img").css("width","1280px");
	    }
	    else{
	         $("#slideshow-list ul li img").css("width","100%")
	    }	        
	}
	function getProvince(){
		$("#search-province").fadeOut(500,function(){
			$("#search-province").html('');
			$("#search-city").html('');
			$("#search-county").html('');
			$.ajax({
	                url:"/getArea?province=1",
	                success:function(data) {
	                    if (data == "") {
	//                        $("#validate_info").html("<font color='#dc143c'>"+"</font>");
	                        return false;
	                    }
	                    if (data && data.r != 1) {
	                        return false;
	                    }
	                    var province_list=data.province_list;
	                    $.each(province_list,function (k,l){
							$("#search-province").append("<a href='javascript:void(0)' onclick=getCity("+l.id+",'"+l.name+"')>"+l.name+"</a>");
	    				});
					}
				})
				$("#search-province").fadeIn();
		})
		$("#search-city").fadeOut();
		$("#search-county").fadeOut();
	}

	function getCity(pid,name){
		$("#search-province").fadeIn();
		$("#search-city").fadeOut(500,function(){
			$("#search-city").html('');
			$("#search-county").html('');
			$.ajax({
                url:"/getArea?province=1&city=1&province_id="+pid,
                success:function(data) {
                    if (data == "") {
//                        $("#validate_info").html("<font color='#dc143c'>"+"</font>");
                        return false;
                    }
                    if (data && data.r != 1) {
                        return false;
                    }
                    var city_list=data.city_list;
                    $.each(city_list,function (k,l){
						$("#search-city").append("<a href='javascript:void(0)' onclick=getCounty("+l.id+",'"+l.name+"')>"+l.name+"</a>");
    				});
				}
			})
			$("#search-city").fadeIn();
		});
		
		$("#search-county").fadeOut();
		address[0]=name+" - ";
		address[1]='';
		address[2]='';
	}

	function getCounty(pid,name){
		$("#search-province").fadeIn();
		$("#search-city").fadeIn();
		$("#search-county").fadeOut(500,function(){
		$("#search-county").html('');
		$.ajax({
                url:"/getArea?province=1&city=1&county=1&city_id="+pid,
                success:function(data) {
                    if (data == "") {
//                        $("#validate_info").html("<font color='#dc143c'>"+"</font>");
                        return false;
                    }
                    if (data && data.r != 1) {
                        return false;
                    }
                    var county_list=data.county_list;
                    $.each(county_list,function (k,l){
						$("#search-county").append("<a href='javascript:void(0)' onclick=showCounty("+l.id+",'"+l.name+"')>"+l.name+"</a>");
    				});
				}
			})
		$("#search-county").fadeIn();
		});
		address[1]=name+" - ";
		address[2]='';
	}
	function showCounty(id,name){
		$("#search-province").fadeOut();
		$("#search-city").fadeOut();
		$("#search-county").fadeOut();
		address[2]=name;
		var item='';
		for(i in address){
			item+=address[i];
		}
		$("#location").val(item);
		$("#location-id").val(id);

	}