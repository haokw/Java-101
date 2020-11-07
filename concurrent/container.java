/**
 * 容器四大类 List Map Set Queue
 */

/**
 * 同步容器线程安全 但性能差
 */
SafeArrayList<T> {
    List<T> c = new ArrayList<>();

    synchronized T get(int idx) {
        return c.get(idx);
    }

    synchronized void add(int idx, T t) {
        c.add(idx, t);
    }

    synchronized boolean addIfNotExist(T t) {
        if (!c.contains(t)) {
            c.add(t);
            return true;
        }
        return false;
    }
}

/**
 * 并发包已提供相关功能包装类
 */
List list = Collections.synchronizedList(new ArrayList());
Set set = Collections.synchronizedSet(new HashSet());
Map map = Collections.synchronizedMap(new HashMap());

/**
 * 组合操作需要注意竞态条件问题
 * 例如 addIfNotExist()
 */

/**
 * 遍历容器要加锁
 */
List list = Collections.synchronizedList(new ArrayList());
synchronized (list) {
    Iterator i = list.iterator();
    while (i.hasNext()) {
        foo(i.next());
    }
}

/**
 * 同步容器 Vector Stack Hashtable
 */

/**
 * 并发容器
 * 使用时
 * 1. 清楚每种容器的特性
 * 2. 选对容器
 * 3. 具体用法，用到的时候看 API 就可以
 *
 * 快速失败机制 Fail-Fast 选对容器就不会触发
 */
List
    // 读写可并行
    CopyOnWriteArrayList

Map
    // key value 不可以为 null
    // key 无序
    ConcurrentHashMap
    // key 有序 跳表实现 性能更好
    ConcurrentSkipListMap

Set
    // 同上
    ConcurrentSkipListSet
    CopyOnWriteArraySet

// 队列需要考虑是否有界 只有 ArrayBlockingQueue 和 LinkedBlockingQueue 支持有界
Queue
    // 阻塞
    // 双端
    BlockingDeque
        LinkedBlockingDeque

    // 单端
    BlockingQeque
        // 内部持有队列 数组实现
        ArrayBlockingQueue
        // 内部持有队列 链表实现
        LinkedBlockingQueue
        // 内部不持有队列 入队操作必须等待消费者队列出对
        SynchronousQueue
        // 融合 LinkedBlockingQueue 与 SynchronousQueue 功能
        LinkedTransferQueue

        // 按照优先级出队
        PriorityBlockingQueue
        // 延时出队
        DelayQueue

    // 非阻塞 并发
    // 单端
    ConcurrentLinkedQueue
    // 双端
    ConcurrentLinkedDeque

/**
 * 线上系统 CPU 突然飙升，
 * 你怀疑有同学在并发场景里使用了 HashMap，
 * 因为在 1.8 之前的版本里并发执行 HashMap.put() 可能会导致 CPU 飙升到 100%，
 * 你觉得该如何验证你的猜测呢？
 *
 * Java7中的HashMap在执行put操作时会涉及到扩容，由于扩容时链表并发操作会造成链表成环，所以可能导致cpu飙升100%。
 */

/**
 * 没有理解为什么concurrentskiplistmap比concurrenthashmap性能好
 *
 * 如果key冲突比较大，hashmap还是要靠链表或者tree来解决冲突的，所以O(1)是理想值。
 * 同时增删改操作很多也影响hashmap性能。这个也是要看冲突情况。也就是说hashmap的稳定性差，
 * 如果很不幸正好偶遇它的稳定性问题，同时又接受不了，就可以尝试skiplistmap，
 * 它能保证稳定性，无论你的并发量是多大，也没有key冲突的问题。
 */
