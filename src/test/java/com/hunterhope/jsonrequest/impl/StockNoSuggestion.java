/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hunterhope.jsonrequest.impl;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author user
 */
public class StockNoSuggestion {

    private String query;
    private List<String> suggestions;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.query);
        hash = 23 * hash + Objects.hashCode(this.suggestions);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockNoSuggestion other = (StockNoSuggestion) obj;
        if (!Objects.equals(this.query, other.query)) {
            return false;
        }
        return Objects.equals(this.suggestions, other.suggestions);
    }

    @Override
    public String toString() {
        return "StockNoSuggestion{" + "query=" + query + "\n, suggestions=" + suggestions + '}';
    }
}
