$("#btnLogin").click(function(){$(this).button("loading");
submit()
});
function callback(c,b){if(!b.status){$("#btnLogin").button("reset");
errorData(b)
}else{if(b.generic!=undefined&&b.generic!=null){var a="<h3>Escolha o ecommerce que deseja acessar:</h3><br><select>";
$.each(b.generic,function(d,e){a+='<option value="'+e.id+'">'+e.name+"</option>"
});
a+='</select><br><button class="btn btn-primary" onclick="submit();">Acessar</button>';
modal(a)
}else{location.href="/ecommerce/admin"
}}}if(getURLParameter("error")!=null&&getURLParameter("error")!=undefined&&getURLParameter("error")!=""&&getURLParameter("error")!="null"){$("#box-alert").html(getErrorMsg(getURLParameter("error")))
}function submit(){if($("select")!=undefined){$("input[name=s]").val($("select").val())
}postJson(0,"/ecommerce-web/admin/login",$("form").serialize()+"&tk="+new Date().getTime())
};