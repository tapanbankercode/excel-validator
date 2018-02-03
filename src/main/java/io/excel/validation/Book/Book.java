package io.excel.validation.Book;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tapan N. Banker
 * @author Tapan N. Banker
 */
public class Book {
    private String title;
    private String author;
    private double price;
    private Date publishDate;

    // Constructor
    public Book() {
    }

    /**
     * Parameter Constructor
     * @param title title
     * @param author author
     * @param price price
     */
    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }


    /**
     * Parameter Constructor
     * @param title title
     * @param author author
     * @param price price
     * @param publishDate publishDate
     */
    public Book(String title, String author, double price, Date publishDate) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishDate = publishDate;
    }

    /**
     * Returns the Title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the Author
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the Author
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Return the price
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return the PublishDate
     * @return
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Set the publishDate
     * @param publishDate
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }


    /**
     * the method return the custom string representation of the object
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(500);

        String stringPublishDate = "";
        //to convert Date to String, use format method of SimpleDateFormat class.
        if(null != getPublishDate()) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            stringPublishDate = dateFormat.format(getPublishDate());
        }
        sb.append(title);
        sb.append(" | ");
        sb.append(author);
        sb.append(" | ");
        sb.append(price);
        sb.append(" | ");
        sb.append(stringPublishDate);
        sb.append("\n ");
        // Convert SB to String and return
        return sb.toString();
    }
}
