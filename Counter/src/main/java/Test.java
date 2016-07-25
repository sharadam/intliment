import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.codec.binary.Base64;

import static java.util.stream.Collectors.toSet;

import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;
import java.util.LinkedHashMap;

public class Test {

	public static void main(String[] args) throws IOException {
		
		String s = "inteliment:pass123";
		String str = "Basic " + new String(Base64.encodeBase64(s.getBytes("UTF-8")));
				System.out.println("str : " + str);
		} 
	
}