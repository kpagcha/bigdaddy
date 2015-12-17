package es.uca.gii.csi.bigdaddy.test;

import es.uca.gii.csi.bigdaddy.data.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.BeforeClass;

import es.uca.gii.csi.bigdaddy.data.Data;

public class TestTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@org.junit.Test
	public void test() {
		try {
			List<Test> a = Test.Select(null, null, null, null, new SimpleDateFormat("dd-MM-yyyy").parse("05-08-2015"));

			for (Test t : a) {
				System.out.println(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
