package com.eztornado.tornadocorebase.repositories.specifications;

public class SpecificationBase {
    protected String getLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%"; // match all query
        } else {
            return "%" + searchTerm + "%";
        }
    }
}
