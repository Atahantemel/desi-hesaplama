! function (e) {
	"use strict";

	function t(t) {
		var n = e("");
		try {
			n = e(t).clone()
		} catch (r) {
			n = e("<span />").html(t)
		}
		return n
	}

	function n(t, n, r) {
		var o = e.Deferred();
		try {
			t = t.contentWindow || t.contentDocument || t;
			var a = t.document || t.contentDocument || t;
			r.doctype && a.write(r.doctype), a.write(n), a.close();
			var i = !1,
				c = function () {
					if (!i) {
						t.focus();
						try {
							t.document.execCommand("print", !1, null) || t.print(), e("body").focus()
						} catch (n) {
							t.print()
						}
						t.close(), i = !0, o.resolve()
					}
				};
			e(t).on("load", c), setTimeout(c, r.timeout)
		} catch (l) {
			o.reject(l)
		}
		return o
	}

	function r(t, r) {
		var a = e(r.iframe + ""),
			i = a.length;
		0 === i && (a = e('<iframe height="0" width="0" border="0" wmode="Opaque"/>').prependTo("body").css({
			position: "absolute",
			top: -999,
			left: -999
		}));
		var c = a.get(0);
		return n(c, t, r).done(function () {
			setTimeout(function () {
				0 === i && a.remove()
			}, 1e3)
		}).fail(function (e) {
			console.error("Failed to print from iframe", e), o(t, r)
		}).always(function () {
			try {
				r.deferred.resolve()
			} catch (e) {
				console.warn("Error notifying deferred", e)
			}
		})
	}

	function o(e, t) {
		var r = window.open();
		return n(r, e, t).always(function () {
			try {
				t.deferred.resolve()
			} catch (e) {
				console.warn("Error notifying deferred", e)
			}
		})
	}

	function a(e) {
		return !!("object" == typeof Node ? e instanceof Node : e && "object" == typeof e && "number" == typeof e.nodeType && "string" == typeof e.nodeName)
	}
	e.print = e.fn.print = function () {
		var n, i, c = this;
		c instanceof e && (c = c.get(0)), a(c) ? (i = e(c), arguments.length > 0 && (n = arguments[0])) : arguments.length > 0 ? (i = e(arguments[0]), a(i[0]) ? arguments.length > 1 && (n = arguments[1]) : (n = arguments[0], i = e("html"))) : i = e("html");
		var l = {
			globalStyles: !0,
			mediaPrint: !1,
			stylesheet: null,
			noPrintSelector: ".no-print",
			iframe: !0,
			append: null,
			prepend: null,
			manuallyCopyFormValues: !0,
			deferred: e.Deferred(),
			timeout: 750,
			title: null,
			doctype: "<!doctype html>"
		};
		n = e.extend({}, l, n || {});
		var d = e("");
		n.globalStyles ? d = e("style, link, meta, base, title") : n.mediaPrint && (d = e("link[media=print]")), n.stylesheet && (d = e.merge(d, e('<link rel="stylesheet" href="' + n.stylesheet + '">')));
		var s = i.clone();
		if (s = e("<span/>").append(s), s.find(n.noPrintSelector).remove(), s.append(d.clone()), n.title) {
			var f = e("title", s);
			0 === f.length && (f = e("<title />"), s.append(f)), f.text(n.title)
		}
		s.append(t(n.append)), s.prepend(t(n.prepend)), n.manuallyCopyFormValues && (s.find("input").each(function () {
			var t = e(this);
			t.is("[type='radio']") || t.is("[type='checkbox']") ? t.prop("checked") && t.attr("checked", "checked") : t.attr("value", t.val())
		}), s.find("select").each(function () {
			var t = e(this);
			t.find(":selected").attr("selected", "selected")
		}), s.find("textarea").each(function () {
			var t = e(this);
			t.text(t.val())
		}));
		var u = s.html();
		try {
			n.deferred.notify("generated_markup", u, s)
		} catch (p) {
			console.warn("Error notifying deferred", p)
		}
		if (s.remove(), n.iframe) try {
			r(u, n)
		} catch (m) {
			console.error("Failed to print from iframe", m.stack, m.message), o(u, n)
		} else o(u, n);
		return this
	}
}(jQuery);