package org.books.enums;

public enum RoleType {


    /*
    INSERT INTO roles(role_type) VALUES('ROLE_ADMINISTRATOR');
    INSERT INTO roles(role_type) VALUES('ROLE_USER');
    INSERT INTO roles(role_type) VALUES('ROLE_MODERATOR');
    */

    ROLE_USER, /* least privileged */
    ROLE_ADMINISTRATOR,
    ROLE_MODERATOR

}