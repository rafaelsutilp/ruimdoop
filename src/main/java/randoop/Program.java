package randoop;

import randoop.bin.Calculadora;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class Program {
    static List<Map<String,List<String>>> objHash = new ArrayList<>();
    static Random genRandom = new Random();
    static String PATH = "C:\\Users\\rafae\\IdeaProjects\\Ruimdoop\\src\\main\\java\\randoop\\bin\\myclasses.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        long startTime = System.currentTimeMillis();
        int counter = 0;
        String file =
                "package randoop.bin;\n\n" +
                "import org.junit.jupiter.api.Assertions;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import java.time.LocalDate;\n\n" +
                "public class RegressionTest {\n";
        List<String> allSeqs = new ArrayList<>(){{add("");}};
        init();
        boolean success = true;
        while(System.currentTimeMillis()-startTime < 1000){
            // Ler o nome da classe do txt
            String className = "";
            BufferedReader buffRead = new BufferedReader(new FileReader(PATH));
            int p = 0;
            while(p == 0)
                p = genRandom.nextInt(2);
            for(int i=0; i<p; i++){
                className = buffRead.readLine();
            }
            Class<?> clazz = Class.forName("randoop.bin." + className);
            // Seleciona uma sequencia aleatoriamente
            int posSeq = genRandom.nextInt(allSeqs.size());
            String seqs = allSeqs.get(posSeq);
            // Gerar objeto com construtor
            String val="";
            if(genRandom.nextInt(8) % 7 == 0 && success == true){
                success = false;
                try{
                    // Pegar os parametros do construtor
                    Constructor<?>[] constructor = clazz.getDeclaredConstructors();
                    List<String> constructorParams = getConstructorParamsTypes(constructor);
                    // Criar uma variavel
                    val = className.toLowerCase() + genRandom.nextInt(1000);
                    // Criar a linha de Código
                    String codeLine = className + " " + val + " = new " + className + "(";
                    //System.out.println("\n===\n");
                    if(constructorParams.size() != 0){
                        for(String param : constructorParams){
                            // trazer um obj ou literal
                            param = param.replace("randoop.bin.", "");
                            //System.out.println(param);
                            codeLine += objHash.get(posSeq).get(param).get(genRandom.nextInt(objHash.get(posSeq).get(param).size())) + ", ";
                            //System.out.println("param");
                        }
                        codeLine += "EOF";
                        codeLine = codeLine.replace(", EOF", ");\n");
                    }else{
                        codeLine += ");\n";
                    }
                    //System.out.println(codeLine);
                    codeLine = "        " + codeLine;
                    // adiciona o codigo na sequencia
                    seqs += codeLine;
                    // Adicionar a variavel no hash de objetos
                    Map<String,List<String>> tmp = copyMap(objHash.get(posSeq));
                    objHash.add(tmp);
                    try{
                        // Add variavel
                        //objHash.get(posSeq).get(className).add(val);
                        objHash.get(objHash.size()-1).get(className).add(val);
                    }
                    catch(Exception e){
                        // Cria lista se tiver vazio
                        //objHash.get(posSeq).put(className, new ArrayList<String>(){{add(val);}});
                        String finalVal = val;
                        objHash.get(objHash.size()-1).put(className, new ArrayList<>() {{
                            add(finalVal);
                        }});
                    }
                    // Adicionar seqs a allSeqs
                    allSeqs.add(seqs);
                }catch (Exception e){}
            }
            // usar metodo no objeto
            else {
                try{
                    // Lista de Metodos da classe
                    Method[] methods = clazz.getDeclaredMethods();
                    List<String> actualMethods = getMethodNames(methods);
                    // Pegar um metodo aleatório
                    int rdn = genRandom.nextInt(actualMethods.size());
                    String method = actualMethods.get(rdn);
                    String codeLine = "";
                    // Pegar retorno do metodo
                    String returnType = methods[rdn].getReturnType().getName();
                    val = returnType.toLowerCase() + genRandom.nextInt(1000);
                    val = val.replace("java.lang.", "");
                    if(returnType != "void")
                        codeLine = returnType + " " + val + " = ";
                    // Pegar parametros do metodo
                    List<String> methodParams = new ArrayList<>();
                    for(Method x : methods){
                        if(x.getName() == method){
                            methodParams = getParamsTypes(x);
                        }
                    }
                    // Pega um objeto para aplicar o metodo
                    String var = objHash.get(posSeq).get(className).get(genRandom.nextInt(objHash.get(posSeq).get(className).size()));
                    // Criar a linha de Código
                    codeLine += var + "." + method + "(";
                    List<String> argValues = new ArrayList<>();
                    int c = 0;
                    String parametro = "";
                    if(methodParams.size() != 0){
                        for(String param : methodParams){
                            // trazer um obj ou literal
                            var temp = objHash.get(posSeq).get(param).get(genRandom.nextInt(objHash.get(posSeq).get(param).size()));
                            argValues.add(temp);
                            parametro = param;
                            codeLine += temp + ", ";
                            c++;
                        }
                        codeLine += "EOF";
                        codeLine = codeLine.replace(", EOF", ");\n");
                    }else{
                        codeLine += ");\n";
                    }
                    codeLine = "        " + codeLine;
                    // adiciona o codigo na sequencia
                    seqs += codeLine;
                    // linha de teste
                    if(!val.startsWith("void")){
                        counter++;
                        Calculadora op = new Calculadora();
                        Object result = new Object();
                        switch(parametro){
                            case "double":
                                result = methods[rdn].invoke(op, Double.parseDouble(argValues.get(0)),  Double.parseDouble(argValues.get(1)));
                            break;
                            case "int":
                                result = methods[rdn].invoke(op, Integer.parseInt(argValues.get(0)),  Integer.parseInt(argValues.get(1)));
                            break;
                            case "java.lang.String":
                                result = methods[rdn].invoke(op, argValues.get(0));
                            break;
                            case "boolean":
                                result = methods[rdn].invoke(op, Boolean.parseBoolean(argValues.get(0)),  Boolean.parseBoolean(argValues.get(1)));
                            break;
                        }
                        String answer = (val.startsWith("boolean")) ? "true" : "XXX";
                        // construir assert
                        /*
                        String[] execLines = seqs.split("\n");
                        for(String execLine : execLines){
                        }
                        */
                        String test = "        Assertions.assertEquals(" + val + ", " + result + ");\n";
                        seqs += test;
                        // Adicionar seqs + test no file
                        file +=
                            "    @Test\n" +
                            "    public void test" + counter + "() throws Throwable {\n" +
                            seqs + "    }\n";
                    }
                    var tmp = copyMap(objHash.get(posSeq));
                    try {
                        tmp.get(returnType).add(val);
                    }catch (Exception e){
                        String finalVal1 = val;
                        tmp.put(returnType, new ArrayList<>(){{add(finalVal1);}});
                    }
                    objHash.add(tmp);
                    // Adicionar seqs a allSeqs
                    allSeqs.add(seqs);
                }catch (Exception e){}
            }
        }
        file += "}\n";
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\rafae\\IdeaProjects\\Ruimdoop\\src\\test\\java\\randoop\\bin\\RegressionTest.java"));
        bufferedWriter.write(file);
        bufferedWriter.close();
        System.out.println(file);
        /*
        for(String asd : allSeqs){
            System.out.println(asd);
            System.out.println("\n\n");
        }*/
    }

    private static void addToFile(List<String> allSeqs) throws IOException {
        String file =
            "import org.junit.jupiter.api.Assertions;\n" +
            "import org.junit.jupiter.api.Test;\n" +
            "public class RegressionTest {\n";
        int i=0;
        for(String seq : allSeqs){
            if(!seq.contains("Assertions"))
                continue;
            i++;
            file +=
            "   @Test\n" +
            "   public void test" + i + "() throws Throwable {\n" +
            seq +
            "   }\n\n";
        }
        file += "}\n";

        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\rafae\\IdeaProjects\\Ruimdoop\\src\\test\\java\\randoop\\bin\\randoopTest.java"));
        bufferedWriter.write(file);
        bufferedWriter.close();
    }

    private static void init(){
        objHash.add(new HashMap<>());
        objHash.get(0).put("short", new ArrayList<>() {
            {
                add("(short)0");
                add("(short)-1000");
                add("(short)-500");
                add("(short)-250");
                add("(short)-100");
                add("(short)100");
                add("(short)250");
                add("(short)500");
                add("(short)1000");
                //add("null");
            }
        });
        objHash.get(0).put("int", new ArrayList<>() {
            {
                add("0");
                add("-1000");
                add("-500");
                add("-250");
                add("-100");
                add("100");
                add("250");
                add("500");
                add("1000");
                //add("null");
            }
        });
        objHash.get(0).put("long", new ArrayList<>() {
            {
                add("0");
                add("-1000");
                add("-500");
                add("-250");
                add("-100");
                add("100");
                add("250");
                add("500");
                add("1000");
                //add("null");
            }
        });
        objHash.get(0).put("java.lang.String", new ArrayList<>() {
            {
                add("\"HelLo\"");
                add("\"tOp\"");
                add("\"hI\"");
                add("\"NicE\"");
                add("\"Go\"");
                add("\"lOrEm\"");
                add("\"IpsUm\"");
                add("\"vAlEu\"");
                //add("null");
            }
        });
        objHash.get(0).put("java.time.LocalDate", new ArrayList<>() {
            {
                add("LocalDate.now()");
                add("LocalDate.parse(\"2022-01-10T23:59:0.0\")");
                add("LocalDate.parse(\"2021-02-20T20:50:0.0\")");
                add("LocalDate.parse(\"2020-03-30T16:40:0.0\")");
                add("LocalDate.parse(\"2019-04-05T12:30:0.0\")");
                add("LocalDate.parse(\"2018-06-15T10:20:0.0\")");
                add("LocalDate.parse(\"2017-09-25T08:10:0.0\")");
                add("LocalDate.parse(\"2015-10-27T05:15:0.0\")");
                add("LocalDate.parse(\"2014-11-03T03:05:0.0\")");
                add("LocalDate.parse(\"2013-12-11T00:00:0.0\")");
                //add("null");
            }
        });
        objHash.get(0).put("double", new ArrayList<>() {
            {
                add("1.99");
                add("-1.55");
                add("0.0");
                add("0.55");
                add("105.5");
                add("-105.5");
                add("10.953");
                add("-10.953");
                add("3.14159");
                //add("null");
            }
        });
        objHash.get(0).put("float", new ArrayList<>() {
            {
                add("(float)1.99");
                add("(float)-1.55");
                add("(float)0.0");
                add("(float)0.55");
                add("(float)105.5");
                add("(float)-105.5");
                add("(float)10.953");
                add("(float)-10.953");
                add("(float)3.14159");
                //add("null");
            }
        });
        objHash.get(0).put("char", new ArrayList<>() {
            {
                add("'a'");
                add("'b'");
                add("'c'");
                add("'d'");
                add("'e'");
                add("'h'");
                add("'x'");
                add("'y'");
                add("'z'");
                //add("null");
            }
        });
        objHash.get(0).put("boolean", new ArrayList<>() {
            {
                add("true");
                add("true");
                add("true");
                add("true");
                add("true");
                add("false");
                add("false");
                add("false");
                add("false");
                add("false");
                //add("null");
            }
        });
    }

    private static List<String> getMethodNames(Method[] methods) {
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods)
            methodNames.add(method.getName());
        return methodNames;
    }

    private static List<String> getConstructorParamsTypes(Constructor<?>[] constructors) {
        List<String> constructorParamsTypes = new ArrayList<>();
        for (Constructor<?> constructor : constructors)
            for(Class<?> cls : constructor.getParameterTypes())
                constructorParamsTypes.add(cls.getTypeName());
        return constructorParamsTypes;
    }

    private static List<String> getParamsTypes(Method method){
        List<String> paramsTypes = new ArrayList<>();
        for(Class<?> cl : method.getParameterTypes()){
            paramsTypes.add(cl.getName());
        }
        return paramsTypes;
    }

    public static <K, V> Map<K, V> copyMap(Map<K, V> original)
    {
        Map<K, V> second_Map = new HashMap<>();
        // Start the iteration and copy the Key and Value
        // for each Map to the other Map.
        for (Map.Entry<K, V> entry : original.entrySet()) {
            // using put method to copy one Map to Other
            second_Map.put(entry.getKey(), (V) new ArrayList((Collection) entry.getValue()));
        }
        return second_Map;
    }
}