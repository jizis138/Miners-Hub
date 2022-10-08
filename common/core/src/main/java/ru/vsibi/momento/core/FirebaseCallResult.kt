/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.momento.core

sealed class FirebaseCallResult{
    class Success <T> (val data : T) : FirebaseCallResult()
    class Error : FirebaseCallResult()
}