package com.viveksb007.libgenio;

import com.viveksb007.libgenio.filter.BookElementFilter;
import com.viveksb007.libgenio.model.Book;

import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class BookExtractor {

    List<Book> extractBooksFromDocument(Document document) {
        return Optional.ofNullable(document)
                .map(doc -> doc.getElementsByTag("tr"))
                .map(elements -> elements
                        .stream()
                        .filter(new BookElementFilter())
                        .map(Element::children)
                        .map(this::getBookFromBookElement)
                        .collect(Collectors.toList())
                ).orElseGet(ArrayList::new);
    }

    @NotNull
    private Book getBookFromBookElement(Elements bookElements) {
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
        return book;
    }

}
