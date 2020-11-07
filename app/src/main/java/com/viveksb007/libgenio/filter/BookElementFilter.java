package com.viveksb007.libgenio.filter;

import org.jsoup.nodes.Element;

import java.util.function.Predicate;

public class BookElementFilter implements Predicate<Element> {
    @Override
    public boolean test(Element element) {
        return "top".equals(element.attr("valign"))
                && ("#C6DEFF".equals(element.attr("bgcolor"))
                || "".equals(element.attr("bgcolor")));
    }
}
