package com.tinder.utils;

import java.sql.SQLException;

public interface FunctionEX <A, B>{
    B apply (A a) throws SQLException;
}
