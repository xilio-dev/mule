package cn.xilio.project.domain.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
@Component
public class VerificationCodeCache {

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 存储验证码并设置过期时间
     *
     * @param key      用户标识（如邮箱地址）
     * @param code     验证码
     * @param duration 过期时间（秒）
     */
    public void put(String key, String code, long duration) {
        cache.put(key, code);
        AtomicBoolean removed = new AtomicBoolean(false);
        scheduler.schedule(() -> {
            if (!removed.get()) {
                cache.remove(key);
            }
        }, duration, TimeUnit.SECONDS);
    }

    /**
     * 获取验证码
     *
     * @param key 用户标识
     * @return 验证码，如果已过期则返回 null
     */
    public String get(String key) {
        return cache.get(key);
    }

    /**
     * 验证验证码并移除
     *
     * @param key   用户标识
     * @param input 用户输入的验证码
     * @return 验证结果
     */
    public boolean verifyAndRemove(String key, String input) {
        String storedCode = cache.get(key);
        if (storedCode != null && storedCode.equals(input)) {
            cache.remove(key);
            return true;
        }
        return false;
    }

    /**
     * 清理缓存
     */
    public void clear() {
        cache.clear();
    }
}
