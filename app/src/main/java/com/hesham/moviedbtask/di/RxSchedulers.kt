package com.hesham.moviedbtask.di

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulers {
    fun io(): Scheduler
    fun main(): Scheduler
    fun test(): Scheduler
}

class RxSchedulersImpl : RxSchedulers {
    override fun io(): Scheduler = Schedulers.io()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
    override fun test(): Scheduler = Schedulers.trampoline()
}

fun <T> Single<T>.applySchedulers(rxSchedulers: RxSchedulers = RxSchedulersImpl()): Single<T> {
    return compose {
        it.subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
    }
}

fun <T> Observable<T>.applySchedulers(rxSchedulers: RxSchedulers = RxSchedulersImpl()): Observable<T> {
    return compose {
        it.subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
    }
}

fun Completable.applySchedulers(rxSchedulers: RxSchedulers = RxSchedulersImpl()): Completable {
    return compose {
        it.subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
    }
}