/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ThinhTPT
 */
public class CartObject implements Serializable {

    private Map<Integer, CartDetails> items;
    private long total;
    private int amount;

    public Map<Integer, CartDetails> getItems() {
        return items;
    }

    public boolean addItemToCart(int productId, String name, int price, int maxAmount) {
        // 1. Check existed items
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        int newAmount = 1;
        CartDetails detail = new CartDetails(name, 0, price, maxAmount);

        // 2. Check existed product
        if (this.items.containsKey(productId)) {
            detail = this.items.get(productId);
            newAmount += detail.getAmount();
        }
        if (newAmount <= maxAmount) {
            detail.increaseAmount();
            detail.calculateSubTotal();
            detail.setMaxAmount(maxAmount);
            detail.setCartErr("");
            this.items.put(productId, detail);
            this.calculateAmountAndTotal();
            return true;
        } else {
            return false;
        }
    }

    public void removeItemFromCart(int id) {
        // 1. Check existed items
        if (this.items == null) {
            return;
        }

        // 2. Check existed product
        if (this.items.containsKey(id)) {
            this.items.remove(id);
            if (this.items.isEmpty()) {
                this.items = null;
            } else {
                this.calculateAmountAndTotal();
            }
        }
    }

    public void updateAmount(int productId, int newAmount, int maxAmount) {
        // 1. Check existed items
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(productId)) {
            CartDetails detail = this.items.get(productId);
            detail.setAmount(newAmount);
            detail.calculateSubTotal();
            detail.setMaxAmount(maxAmount);
            detail.setCartErr("");
            this.calculateAmountAndTotal();
        }
    }

    public void updateItem(int productId, CartDetails detail) {
        // 1. Check existed items
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(productId)) {
            this.items.put(productId, detail);
        }
    }

    public void calculateAmountAndTotal() {
        this.total = 0;
        this.amount = 0;
        if (this.items != null) {
            for (int key : this.items.keySet()) {
                CartDetails detail = this.items.get(key);
                this.total += detail.getSubTotal();
                this.amount += detail.getAmount();

            }
        }
    }

    public void setAmountError(int productId, String message) {
        if (this.items.containsKey(productId)) {
            CartDetails detail = this.items.get(productId);
            detail.setCartErr(message);
        }
    }

    public void clearAmountError(int productId) {
        if (this.items.containsKey(productId)) {
            CartDetails detail = this.items.get(productId);
            detail.setCartErr("");
        }
    }

    public long getTotal() {
        return total;
    }

    public int getAmount() {
        return amount;
    }

    public boolean checkValidCart() {
        if (this.items != null) {
            for (int key : this.items.keySet()) {
                CartDetails detail = this.items.get(key);
                if (!detail.getCartErr().equals("")) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

}
