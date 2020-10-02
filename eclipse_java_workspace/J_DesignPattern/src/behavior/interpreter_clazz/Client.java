package behavior.interpreter_clazz;


public class Client
{
    private static Context ctx;
    private static Expression exp ;

    //可应用的场景比较少。在软件开发中，需要定义语言文法的应用实例非常少，所以这种模式很少被使用到
    public static void main(String[] args)
    {
        ctx = new Context();

		Variable x = new Variable("x");
        Variable y = new Variable("y");
		Constant c = new Constant(true);

        ctx.assign(x, false);
        ctx.assign(y, true);

        exp = new Or( new And(c, x) , new And(y, new Not(x)));
		System.out.println( "x = " + x.interpret(ctx));
		System.out.println( "y = " + y.interpret(ctx));
		System.out.println( exp.toString() + " = " + exp.interpret(ctx));
    }
}
