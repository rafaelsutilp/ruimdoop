package randoop;


import randoop.bin.Endereco;
import randoop.src.MethodInvocationUtils;
import randoop.src.RuntimeCompiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

public class Program {
    static Map<String,List<String>> objHash = new HashMap<String, List<String>>();
    static Random genRandom = new Random();
    static String PATH = "C:\\Users\\rafae\\IdeaProjects\\Ruimdoop\\src\\main\\java\\randoop\\bin\\myclasses.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        init();
        long startTime = System.currentTimeMillis();
        List<String> allSeqs = new ArrayList<String>();

        while(System.currentTimeMillis()-startTime < 100){
            //objHash.clear();
            //init();
            // Ler o nome da classe do txt
            String className = "";
            BufferedReader buffRead = new BufferedReader(new FileReader(PATH));
            int p = 0;
            while(p == 0)
                p = genRandom.nextInt(5);
            for(int i=0; i<p; i++){
                className = buffRead.readLine();
            }

            Class<?> clazz = Class.forName("randoop.bin." + className);

            // Seleciona uma sequencia aleatoriamente
            String seqs;
            if (allSeqs.size() == 0)
                seqs = "";
            else
                seqs = allSeqs.get(allSeqs.size()-1);
                //seqs = allSeqs.get(genRandom.nextInt(allSeqs.size()));

            // Gerar objeto com construtor
            if(genRandom.nextInt(10) % 5 == 0){
                try{
                    // Pegar os parametros do construtor
                    Constructor<?>[] constructor = clazz.getDeclaredConstructors();
                    List<String> constructorParams = getConstructorParamsTypes(constructor);


                    // Criar uma variavel
                    String val = className.toLowerCase() + genRandom.nextInt(100);

                                        // Criar a linha de Código
                    String codeLine = className + " " + val + " = new " + className + "(";
                    //System.out.println("\n===\n");
                    if(constructorParams.size() != 0){
                        for(String param : constructorParams){
                            // trazer um obj ou literal
                            param = param.replace("randoop.bin.", "");
                            //System.out.println(param);
                            codeLine += objHash.get(param).get(genRandom.nextInt(objHash.get(param).size())) + ", ";
                        }
                        codeLine += "EOF";
                        codeLine = codeLine.replace(", EOF", ");\n");
                    }else{
                        codeLine += ");\n";
                    }
                    //System.out.println(codeLine);
                    // adiciona o codigo na sequencia
                    seqs += codeLine;

                    // Testar seqs

                    // Adicionar seqs a allSeqs
                    allSeqs.add(seqs);

                    // Adicionar a variavel no hash de objetos
                    try{
                        // Add variavel
                        objHash.get(className).add(val);
                    }
                    catch(Exception e){
                        // Cria lista se tiver vazio
                        objHash.put(className, new ArrayList<String>(){{add(val);}});
                    }
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
                    // Criar uma variavel
                    String val = returnType.toLowerCase() + genRandom.nextInt(100);
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
                    String var = objHash.get(className).get(genRandom.nextInt(objHash.get(className).size()));

                    // Criar a linha de Código
                    codeLine += var + "." + method + "(";
                    if(methodParams.size() != 0){
                        for(String param : methodParams){
                            // trazer um obj ou literal
                            codeLine += objHash.get(param).get(genRandom.nextInt(objHash.get(param).size())) + ", ";
                        }
                        codeLine += "EOF";
                        codeLine = codeLine.replace(", EOF", ");\n");
                    }else{
                        codeLine += ");\n";
                    }

                    // atribuir

                    // adiciona o codigo na sequencia
                    seqs += codeLine;

                    // Testar seqs

                    // Adicionar seqs a allSeqs
                    allSeqs.add(seqs);

                }catch (Exception e){}
            }

        }
        for(String asd : allSeqs){
            System.out.println(asd);
            System.out.println("\n\n");
        }
/*
        try {
            // Classe a ser adicionada
            Class<?> clazz = Class.forName("randoop.bin.Endereco");

            // Nome da classe
            String className = clazz.getName().replace("randoop.bin.", "");

            // Lista dos Parametros do Construtor
            Constructor<?>[] constructor = clazz.getDeclaredConstructors();
            List<String> constructorParams = getConstructorParamsTypes(constructor);

            // Linha para declaração do objeto
            String line = className + " " + className.toLowerCase() + genRandom.nextInt(500) + " = new " +
                    className + "(";
            for(String param : constructorParams)
                line += objHash.get(param) + ",";
            line += "EOF";
            line = line.replace(",EOF", ");\n");


            // Lista de Metodos da classe
            Method[] methods = clazz.getDeclaredMethods();
            List<String> actualMethods = getMethodNames(methods);

            // Pegar um metodo aleatório
            String method = actualMethods.get(genRandom.nextInt(actualMethods.size()));

            // Pegar parametros do metodo
            List<String> methodParams = new ArrayList<>();
            for(Method x : methods){
                if(x.getName() == method){
                    methodParams = getParamsTypes(x);
                }
            }

            // Sequencias de codigo
            String seqs;
            if(Seqs.size() == 0){
                seqs = "";
            }
            else{
                seqs = Seqs.get(genRandom.nextInt(Seqs.size()));
            }


            String newSeq =
                    seqs +
                    className + " b1 new " + className + "(";
            if(methodParams.size() != 0){
                for (String x : methodParams){
                    newSeq += x + ",";
                }
                newSeq += "EOF";
                newSeq = newSeq.replace(",EOF", ");\n");
            }else{
                newSeq += ");\n";
            }




            //System.out.println(className);
            //System.out.println(paramsTypes);
            //System.out.println(line);
            //System.out.println(actualMethods);
            //System.out.println(method);
            //System.out.println(methodParams);
            //System.out.println(newSeq);



            String codeA =
                    //"import org.junit.jupiter.api.Assertions;" + "\n" +
                    //"import org.junit.jupiter.api.Test;" + "\n" +
                    //"import java.util.Comparator;" + "\n" +
                    "public class RegressionTest0 {" + "\n" +
                    //"   @Test" + "\n" +
                    "   public static void test0001(String name) {" + "\n" +
                    "       System.out.println(\"Hello, \"+name);" + "\n" +
                    //"       Assertions.assertEquals(\"Recife\", \"Recife\");" + "\n" +
                    "   }" + "\n" +
                    "}" + "\n";

            String x = buildFileContent(line);
            //System.out.println(x);

            RuntimeCompiler r = new RuntimeCompiler();
            r.addClass("TestJUnit", x);
            r.compile();

            MethodInvocationUtils.invokeStaticMethod(r.getCompiledClass("TestJUnit"),
                    "TestJunit", "");



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private static void init(){
        objHash.put("int", new ArrayList<String>(){
            {
                add("0");
                add("-99");
                add("99");
                add("50");
                add("-50");
            }
        });
        objHash.put("java.lang.String", new ArrayList<String>(){
            {
                add("\"hello\"");
                add("\"top\"");
                add("\"hi\"");
                add("\"nice\"");
                add("\"go\"");
            }
        });
        objHash.put("java.time.LocalDate", new ArrayList<String>(){
            {
                add("LocalDate.now()");
            }
        });
        objHash.put("double", new ArrayList<String>(){
            {
                add("1.99");
                add("-1.55");
                add("0.0");
                add("0.55");
                add("105.5");
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

    private static String buildFileContent(String line){
        // Conteudo do arquivo
        String fileContent =
            //"import org.junit.Test;\n" +
            //"import static org.junit.Assert.assertTrue;\n" +
            "package randoop;\n" +
            "import randoop.bin.Endereco;\n" +
            "public class TestJUnit {\n" +
            //"   @Test\n" +
            "   public void TestJunit(){\n" +
            "       " + line +
            "   }\n" +
            "}\n";

        return fileContent;
    }
}

// hash[class] -> List[methods]
