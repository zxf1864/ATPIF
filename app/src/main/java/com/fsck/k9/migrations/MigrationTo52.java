package com.fsck.k9.migrations;


import android.database.sqlite.SQLiteDatabase;


class MigrationTo52 {
    public static void addMoreMessagesColumnToFoldersTable(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE folders ADD more_messages TEXT default \"unknown\"");
    }
}
