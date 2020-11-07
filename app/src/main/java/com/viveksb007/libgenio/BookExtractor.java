package com.viveksb007.libgenio;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

class BookExtractor {

    List<Book> extractBooksFromDocument(Document document) {
        List<Book> bookList = new ArrayList<>();
        if (document == null) return new ArrayList<>();
        Elements elements = document.getElementsByTag("tr");
        for (Element element : elements) {
            if ("top".equals(element.attr("valign")) && ("#C6DEFF".equals(element.attr("bgcolor")) || "".equals(element.attr("bgcolor")))) {
                Elements bookElements = element.children();
                Book book = new Book();
                book.setID(bookElements.get(0).text());
                Elements authors = bookElements.get(1).children();
                StringBuilder author = new StringBuilder();
                for (Element authorElements : authors) {
                    author.append(authorElements.text());
                    author.append(", ");
                }
                author.setLength(author.length() - 2);
                book.setAuthor(author.toString());
                book.setTitle((bookElements.get(2).children()).get(0).text());
                book.setPublisher(bookElements.get(3).text());
                book.setYear(bookElements.get(4).text());
                book.setPages(bookElements.get(5).text());
                book.setLanguage(bookElements.get(6).text());
                book.setSize(bookElements.get(7).text());
                book.setExtension(bookElements.get(8).text());
                book.setDownloadLink((bookElements.get(9).children()).get(0).attr("href"));
                bookList.add(book);
            }
        }
        return bookList;
    }

}
