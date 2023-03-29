$(".menu .item").tab(), $(".ui.checkbox").checkbox(), $(".ui.dropdown").dropdown();
var $sideListe = $("#sideListe"),
  $listeButton = $("#listeButton");
$sideListe.sidebar({
  transition: "overlay",
  mobileTransition: "push"
}).sidebar("attach events", $listeButton), $(".toggle .toggle-title").hasClass("active") && $(".toggle .toggle-title.active").closest(".toggle").find(".toggle-inner").show(), $(".toggle .toggle-title").click(function() {
  $(this).hasClass("active") ? $(this).removeClass("active").closest(".toggle").find(".toggle-inner").slideUp(200) : $(this).addClass("active").closest(".toggle").find(".toggle-inner").slideDown(200)
}), $("input.floatDot").on("keypress keyup blur", function() {
  this.value = this.value.replace(/[^0-9.,]/g, "").replace(/(\..*)\./g, "$1").replace(/,/g, ".")
}), $(".ui.icon.button").click(function() {
  HandleUpDown($(this).attr("command"))
}), $("#fiyat").on("click focusin", function() {
  this.value = ""
}), $("#fiyat").on("focusout", function() {
  "" === this.value && (this.value = "0.00")
}), $("#uzunlukBirim").dropdown("set value", 1).dropdown("set text", "MM");
var birimBolme = 1e3,
  birimNe = "MM";
$("#uzunlukBirim").dropdown({
  onChange: function(t) {
    1 == t && (birimNe = "MM"), 2 == t && (birimBolme = 100, birimNe = "CM"), 3 == t && (birimBolme = 1, birimNe = "M")
  }
}), $("#paraBirim").dropdown("set value", 1).dropdown("set text", "tl / kg");
var cartObject, para_birimNe = "tl / kg",
  kur = "1";

function getCookie(t) {
  var e = document.cookie,
    i = e.indexOf(" " + t + "=");
  if (-1 === i && (i = e.indexOf(t + "=")), -1 === i) e = null;
  else {
    i = e.indexOf("=", i) + 1;
    var a = e.indexOf(";", i); - 1 === a && (a = e.length), e = unescape(e.substring(i, a))
  }
  return e
}

function setCookie(t, e, i) {
  var a = new Date;
  a.setDate(a.getDate() + i);
  var n = escape(e) + (null === i ? "" : "; expires=" + a.toUTCString());
  document.cookie = t + "=" + n
}

function addToCart(t) {
  var e = 0;
  if (qty = $("#adet").val(), h_cinsi = $("#h_cinsi").text(), h_ebat = $("#h_ebat").text(), h_malzeme = $("#h_malzeme").text(), h_fiyat = $("#h_fiyat").text(), h_agirlik = $("#h_agirlik").text(), qty = parseFloat(qty), h_fiyat = parseFloat(h_fiyat), h_agirlik = parseFloat(h_agirlik), 0 < cartObject.length)
    for (var i = 0; i < cartObject.length; i += 7)
      if (h_cinsi == cartObject[i + 2] && h_ebat == cartObject[i + 3] && h_malzeme == cartObject[i + 4]) var a = i + (e = 1);
  0 == e ? (cartObject.push(t), cartObject.push(qty), cartObject.push(h_cinsi), cartObject.push(h_ebat), cartObject.push(h_malzeme), cartObject.push(h_fiyat), cartObject.push(h_agirlik)) : cartObject[a] += qty, setCookie("cart", JSON.stringify(cartObject), 7), updateCartView(), $("#listeButton").transition("flash"), $("#listemeEkle").addClass("disabled")
}

function updateCartView() {
  var t = 0,
    e = 0;
  null === cartObject && (cartObject.length = null);
  var i = "",
    a = "";
  if (0 < cartObject.length) {
    for (var n = 0; n < cartObject.length; n += 7) i += "<tr>", i += "<td>" + cartObject[n + 2] + "</td>", i += '<td><h4 class="ui image header">', i += '    <div class="content"> ' + cartObject[n + 3], i += '    <div class="sub header">' + cartObject[n + 4] + " </div>", i += "  </div>", i += " </h4></td>", i += ' <td class="right aligned agirlikGizle">' + numeral(cartObject[n + 6] * cartObject[n + 1]).format("0,0.00") + " kg</td>", i += ' <td class="right aligned fiyatGizle">' + numeral(cartObject[n + 5] * cartObject[n + 1]).format("0,0.00 $") + "</td>", i += ' <td class="left aligned"><div class="ui action right  labeled input">', i += ' <input style="width: 50px;padding: 0px 5px;text-align: right;line-height: 38px;" type="text" id="adetSepet' + n + '" class="adet sepet printSepet" onkeyup="cartUp(' + n + ')" value="' + cartObject[n + 1] + '" >', i += ' <div class="ui basic label sepet " style="width: 40px;    border-radius: 0;border-left: none;padding: 12px 0px;font-size: 12px;"> ADET </div>', i += '     <div class="ui mini   vertical buttons" style="height: 40px;width: 30px;">', i += '     <button class="ui icon button no-print i1 cartplus"  style="font-size: 10px; padding:0" command="Up" onclick="cartPlus(' + n + ');return false;"> <i class="up chevron icon"></i> </button>', i += '    <button class="ui icon button no-print i2 cartminus" style="font-size: 10px; padding:0" command="Down"  onclick="cartMinus(' + n + ');return false;"> <i class="down chevron icon"></i> </button>', i += "   </div>", i += '    <div class="ui animated fade no-print small grey  button butonEkle cartdelete" style="height: 40px;" onclick="cartDelete(' + n + ');return false;" tabindex="0">', i += '    <div class="hidden  content">Sil</div>', i += '    <div class="visible content"> <i class="trash icon"></i> </div>', i += "  </div>", i += "  </div></td>", i += "</tr>", t += cartObject[n + 6] * cartObject[n + 1], e += cartObject[n + 5] * cartObject[n + 1];
    a += '<tr class="ikisinideGizle">', a += '<th colspan="2"></th>', a += '<th class="right aligned agirlikGizle" style="font-size: 16px; font-weight: bold;" id="toplamKilom">' + numeral(t).format("0,0.00") + " kg</th>", a += '<th class="right aligned fiyatGizle" style="font-size: 16px; font-weight: bold;"  id="toplamFiyati">' + numeral(e).format("0,0.00 $") + "</th>", a += "<th></th>", a += "</tr>"
  } else i += "<tr>", i += '<td colspan="6">Listeniz Åu Anda BoÅŸ</td>', i += "</tr>";
  $("#listeBody").html(i), $("#listeFoot").html(a)
}

function cartUp(t) {
  var e = document.getElementById("adetSepet" + t);
  cartObject[t + 1] = e.value, cartString = JSON.stringify(cartObject), setCookie("cart", cartString, 7), setTimeout(function() {
    updateCartView()
  }, 1e3)
}

function cartPlus(t) {
  cartObject[t + 1]++, cartString = JSON.stringify(cartObject), setCookie("cart", cartString, 7), updateCartView()
}

function cartMinus(t) {
  cartObject[t + 1]--, cartObject[t + 1] <= 0 && (1 == confirm("Press a button!") ? cartObject.splice(t, 7) : cartObject[t + 1]++);
  cartString = JSON.stringify(cartObject), setCookie("cart", cartString, 7), updateCartView()
}

function cartDelete(t) {
  cartObject.splice(t, 7), cartString = JSON.stringify(cartObject), setCookie("cart", cartString, 7), updateCartView()
}

function emptyCart() {
  cartObject = [], cartString = JSON.stringify(cartObject), setCookie("cart", cartString, 7), updateCartView()
}

function HandleUpDown(t) {
  var e = $("#adet").val().trim(),
    i = "" !== e ? parseInt(e) : 0;
  switch (t) {
    case "Up":
      i < 99 && (i += 1), $("#loader").fadeOut(), $(".sonuc").animate({
        height: "0"
      }, 500), $(".hesaplananDegerler").hide();
      break;
    case "Down":
      1 < i && (i -= 1), $("#loader").fadeOut(), $(".sonuc").animate({
        height: "0"
      }, 500), $(".hesaplananDegerler").hide()
  }
  $("#adet").val(i)
}
$("#paraBirim").dropdown({
  onChange: function(t) {
    1 == t && (kur = "1", para_birimNe = "tl / kg"), 2 == t && (kur = "2", para_birimNe = "$ / kg"), 3 == t && (kur = "3", para_birimNe = "£ / kg")
  }
}), $("#loader").addClass("gizle"), $("#butonHesapla").click(function() {
  $("#listemeEkle").removeClass("disabled"), formHesaplama($("#butonHesapla").data("yer")), setTimeout(function() {
    $(".field ").hasClass("error") || ($("#loader").fadeIn(), $("#loader").removeClass("gizle"), $(".sonuc").animate({
      height: "0"
    }, 500), $(".hesaplananDegerler").hide(), $("#hesapAlan").find("input, textarea, button, select").attr("disabled", "disabled"), $("#butonHesapla").addClass("disabled"), $(".dropdown").addClass("disabled"), $(".label").addClass("disabled"), $("#butonHesapla").text("HesaplanÄ±yor"), $(".sekilIcerik").fadeOut("slow"), setTimeout(function() {
      $("#loader").fadeOut(), $(".sonuc").animate({
        height: "120"
      }, 500), $(".hesaplanan").fadeIn("slow"), $("#hesapAlan").find("input, textarea, button, select").prop("disabled", !1), $(".dropdown").removeClass("disabled"), $(".label").removeClass("disabled"), $("#butonHesapla").removeClass("disabled"), $("#butonHesapla").text("Hesapla")
    }, 300), setTimeout(function() {
      $(".hesaplananDegerler").fadeIn("slow")
    }, 350))
  }, 300)
}), $(".sideKapat").click(function() {
  $sideListe.sidebar("toggle")
}), $(".ui.basic.modal.temizleListe").modal({
  onApprove: function() {
    emptyCart()
  }
}), $(".listeTemizle").click(function() {
  $(".ui.modal.temizleListe").modal("show")
}), $(".listeYazdir").on("click", function() {
  $(".ui.modal.yazdirListe").modal("show")
}), $(".yazdirButton").on("click", function() {
  document.getElementById("hiddenFiyat").checked ? $(".fiyatGizle").addClass("no-print") : $(".fiyatGizle").removeClass("no-print"), document.getElementById("hiddenAgirlik").checked ? $(".agirlikGizle").addClass("no-print") : $(".agirlikGizle").removeClass("no-print"), document.getElementById("hiddenAgirlik").checked && document.getElementById("hiddenFiyat").checked ? $(".ikisinideGizle").addClass("no-print") : $(".ikisinideGizle").removeClass("no-print"), $("#ele2").print({
    globalStyles: !0,
    mediaPrint: !0,
    stylesheet: null,
    noPrintSelector: ".no-print",
    iframe: !0,
    append: null,
    prepend: null,
    manuallyCopyFormValues: !0,
    deferred: $.Deferred(),
    timeout: 750,
    title: "Bu liste metalist.com.tr aÄŸÄ±rlÄ±k hesaplama sistemi kullanÄ±larak hazÄ±rlanmÄ±ÅŸtÄ±r",
    doctype: "<!doctype html>"
  })
}), window.onload = function() {
  var t = getCookie("cart");
  null == (cartObject = jQuery.parseJSON(t)) && (cartObject = []), updateCartView()
};
var suffixes = {
  1: "'inci",
  5: "'inci",
  8: "'inci",
  70: "'inci",
  80: "'inci",
  2: "'nci",
  7: "'nci",
  20: "'nci",
  50: "'nci",
  3: "'Ã¼ncÃ¼",
  4: "'Ã¼ncÃ¼",
  100: "'Ã¼ncÃ¼",
  6: "'ncÄ±",
  9: "'uncu",
  10: "'uncu",
  30: "'uncu",
  60: "'Ä±ncÄ±",
  90: "'Ä±ncÄ±"
};

function yaziParcala(t) {
  if (void 0 !== t && null != t) {
    var e, i = t.split(" x ");
    for (e = 0; e < i.length; e++) $(".olcu  span:nth-child(" + (e + 1) + ")").empty(), $(".olcu  span:nth-child(" + (e + 1) + ")").append(i[e])
  }
}

function olcuParcala(t, e) {
  if (void 0 !== t && null != t) {
    var i, a = t.split(","),
      n = e.split(",");
    for (i = 0; i < a.length; i++) $(".olcu  span:nth-child(" + (i + 1) + ")").empty(), i < 6 && $(".olcu  span:nth-child(6)").empty(), $(".olcu  span:nth-child(" + (i + 1) + ")").append(n[i] + "" + a[i])
  }
}
numeral.register("locale", "tr", {
  delimiters: {
    thousands: ".",
    decimal: ","
  },
  abbreviations: {
    thousand: "bin",
    million: "milyon",
    billion: "milyar",
    trillion: "trilyon"
  },
  ordinal: function(t) {
    if (0 === t) return "'Ä±ncÄ±";
    var e = t % 10,
      i = t % 100 - e,
      a = 100 <= t ? 100 : null;
    return suffixes[e] || suffixes[i] || suffixes[a]
  },
  currency: {
    symbol: "tl"
  }
}), numeral.locale("tr"), setTimeout(function() {
  $(".hesap_kapsayici").height()
}, 3e3);