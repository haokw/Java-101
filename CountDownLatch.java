
/**
 * 待优化问题
 * 串行运行 效率低
 */
while (needReconciliation) {
    // 查询未对账订单
    pos = getPOrders();

    // 查询派送单
    dos = getDOrders();

    // 执行对账操作
    diff = check(pos, dos);

    // 差异写入差异库
    save(diff);
}

/**
 * 利用并行优化
 * 每次创建新的线程 开销大
 */
while (needReconciliation) {
    // 查询未对账订单
    // pos = getPOrders();
    Thread T1 = new Thread(()->{
        pos = getPOrders();
    });
    T1.start();

    // 查询派送单
    // dos = getDOrders();
    Thread T2 = new Thread(()->{
        dos = getDOrders();
    });
    T2.start();

    // 等待 T1 T2 结束
    T1.join();
    T2.join();

    // 执行对账操作
    diff = check(pos, dos);

    // 差异写入差异库
    save(diff);
}

/**
 * 利用线程池优化
 * 线程不会结束 join 等待同步失效 使用
 * 用 CountDownLatch 实现线程等待
 * 获取对账信息和对账操作之间还是串行的
 */
Executor executor = Executors.newFixedThreadPool(2);

while (needReconciliation) {
    CountDownLatch latch = new CountDownLatch(2);

    // 查询未对账订单
    // pos = getPOrders();
    // Thread T1 = new Thread(()->{
    //     pos = getPOrders();
    // });
    // T1.start();
    executor.execute(()->{
        pos = getPOrders();
        // ⚠️ 需要考虑异常情况释放 防止死锁
        // 方法 设置超时 或者 finally
        latch.countDown();
    });

    // 查询派送单
    // dos = getDOrders();
    // Thread T2 = new Thread(()->{
    //     dos = getDOrders();
    // });
    // T2.start();
    executor.execute(()->{
        dos = getDOrders();
        latch.countDown();
    });

    // 等待 T1 T2 结束
    // T1.join();
    // T2.join();
    latch.await();

    // 执行对账操作
    diff = check(pos, dos);

    // 差异写入差异库
    save(diff);
}

/**
 * 利用生产者 消费者队列优化
 * 用 CyclicBarrier 实现线程等待 两个队列步调一致
 * 计数器有自动重置功能
 */
Vector<P> pos;
Vector<D> dos;

// Executor executor = Executors.newFixedThreadPool(2);
Executor executor = Executors.newFixedThreadPool(1);
final CyclicBarrier barrier = new CyclicBarrier(2, ()->{
    executor.execute(()->checkTask());
});

// ⚠️ 生产速度大于消费速度时会出现 阻塞甚至OOM
void checkTask() {
    P p = pos.remove(0);
    D d = dos.remove(0);

    // 执行对账操作
    diff = check(pos, dos);

    // 差异写入差异库
    save(diff);
}

void checkAll() {
    Thread T1 = new Thread(()->{
        while (needReconciliation) {
            pos.add(getPOrders());
            barrier.await();
        }
    });
    T1.start();

    Thread T2 = new Thread(()->{
        while (needReconciliation) {
            dos.add(getDOrders());
            barrier.await();
        }
    });
    T2.start();
}

/**
 * CountDownLatch 主要用来解决一个线程等待多个线程的场景
 * CyclicBarrier 是一组线程之间相互等待
 */

/**
 * Executor executor = Executors.newFixedThreadPool(1); 是必要的
 * 1.使用线程池是为了异步操作，否则回掉函数是同步调用的，也就是本次对账操作执行完才能进行下一轮的检查。
 * 2.线程数量固定为1，防止了多线程并发导致的数据不一致，因为订单和派送单是两个队列，只有单线程去两个队列中取消息才不会出现消息不匹配的问题。
 */
