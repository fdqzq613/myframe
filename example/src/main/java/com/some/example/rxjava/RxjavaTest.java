package com.some.example.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-04 14:40
 */
@Slf4j
public class RxjavaTest {
    static Disposable disposable = null;
    public static void main(String[] args) {
        //这里默认 观察者和被观察者在同个线程
        //被观察者
        Observable novel= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });
        //创建读者（观察者）
        Observer<String> reader=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                log.info("onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(String value) {
                log.info("onNext:{}",value);
                if("exit".equals(value)){
                    disposable.dispose();//退订
                }
            }

            @Override
            public void onError(Throwable e) {
                log.info("onError:{}",e.getMessage(),e);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };

        novel.subscribe(reader);
        //novel.subscribe(reader);
        //异步 不同线程
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        })
                .observeOn(Schedulers.newThread())//开辟线程
                .subscribeOn(Schedulers.io())//执行在io线程 一个无数量上限的线程池，可以重用空闲的线程
                .subscribe(new Observer<String>() {//观察者
                    @Override
                    public void onSubscribe(Disposable d) {
                        log.info("onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        log.info("onNext:{}",value);
                        if("exit".equals(value)){
                            disposable.dispose();//退订
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.info("onError:{}",e.getMessage(),e);
                    }

                    @Override
                    public void onComplete() {
                        log.info("onComplete");
                    }
                });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
