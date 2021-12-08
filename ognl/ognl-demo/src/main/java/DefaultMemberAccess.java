import ognl.MemberAccess;

import java.lang.reflect.Member;
import java.util.Map;

public class DefaultMemberAccess implements MemberAccess {
    @Override
    public Object setup(Map map, Object o, Member member, String s) {
        return null;
    }

    @Override
    public void restore(Map map, Object o, Member member, String s, Object o1) {

    }

    @Override
    public boolean isAccessible(Map map, Object o, Member member, String s) {
        return true;
    }
}
