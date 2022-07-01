import ognl.MemberAccess;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import java.util.Map;

public class OgnlExpression {

    private Object expression;

    public OgnlExpression(String expressionString) throws OgnlException {
        expression = Ognl.parseExpression(expressionString);
    }

    public Object getExpression()
    {
        return expression;
    }

    public Object getValue(OgnlContext context, Object rootObject) throws OgnlException {
        return Ognl.getValue(getExpression(), context, rootObject);
    }

    public void setValue(OgnlContext context, Object rootObject, Object value) throws OgnlException {
        Ognl.setValue(getExpression(), context, rootObject, value);
    }

    public static void main(String[] args) {

        try {
            MemberAccess memberAccess = new DefaultMemberAccess();
            OgnlExpression expression = new OgnlExpression("new int[1024*1024*1024][2]");

            Demo demo = new Demo("trganda");
            Map context = Ognl.createDefaultContext(demo, memberAccess);
            Object ret = expression.getValue((OgnlContext) context, demo);

            System.out.println(ret);
        } catch (OgnlException e) {
            e.printStackTrace();
        }

    }

}
