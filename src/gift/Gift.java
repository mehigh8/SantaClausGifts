package gift;

import enums.Category;

import java.util.Objects;

/**
 * Clasa care stocheaza informatiile despre un cadou.
 */
public final class Gift {
    private String productName;
    private Double price;
    private Category category;
    private int quantity;

    public Gift(final String productName, final Double price, final Category category,
                final int quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Functie care decrementeaza cantitatea cadoului.
     */
    public void decreaseQuantity() {
        quantity--;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gift gift = (Gift) o;
        return quantity == gift.quantity
                && productName.equals(gift.productName)
                && price.compareTo(gift.price) == 0
                && category == gift.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, category, quantity);
    }
}
