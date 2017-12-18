package com.fsck.k9;

/**
 * Created by afei on 2017/12/5.
 */

public interface BaseAccount {
    String getEmail();
    void setEmail(String email);
    String getDescription();
    void setDescription(String description);
    String getUuid();
}
