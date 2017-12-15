package quiz.jvm;


public class FinalizeEscapeGC {
	public static FinalizeEscapeGC SAVE_HOOK = null;

	public void isAlive() {
		System.out.println("yes,i am still alive��)");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize mehtod executed!");
		FinalizeEscapeGC.SAVE_HOOK = this; //���������ʹ�����ַ��������ȶ���
	}

	public static void main(String[] args) throws Throwable {
		SAVE_HOOK = new FinalizeEscapeGC();
		// �����һ�γɹ������Լ�
		SAVE_HOOK = null;
		System.gc();
		// ��Ϊfinalize�������ȼ��ܵͣ�������ͣ0.5���Եȴ���
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no,i am dead��(");
		}
		/* ������δ������������ȫ��ͬ����������Ծ�ȴʧ���� */
		SAVE_HOOK = null;
		System.gc(); //��Ϊfinalize()�Ѿ�ִ�й�һ����,�����ٴ�ִ��
		// ��Ϊfinalize�������ȼ��ܵͣ�������ͣ0.5���Եȴ���
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no,i am dead����");
		}
	}
}
