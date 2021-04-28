package com.example.demo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.*;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class DemoApplication {
    static Logger logger = Logger.getLogger("");
    ITextStorage<?> var;

    public int countMatchingCharacters(ITextStorage<?> other) {
        int matchingCharsCount = 0;

        for (int i = 0; i < this.var.get().length(); i++) {
            if(this.var.get().charAt(i) == other.get().charAt(i)){
                matchingCharsCount++;
            }
        }

        return matchingCharsCount;
    }

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


        String jsonStr = "{" +
                "   \"types\":[" +
                "      {" +
                "         \"slot\":1," +
                "         \"type\":{" +
                "            \"name\":\"grass\"," +
                "            \"url\":\"https://pokeapi.co/api/v2/type/12/\"" +
                "         }" +
                "      }," +
                "      {" +
                "         \"slot\":2," +
                "         \"type\":{" +
                "            \"name\":\"poison\"," +
                "            \"url\":\"https://pokeapi.co/api/v2/type/4/\"" +
                "         }" +
                "      }" +
                "   ]" +
                "}";
        

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode types = objectMapper.readTree(jsonStr).withArray("types");

        List<String> typeNames2 = IntStream.range(0, types.size())
                .mapToObj(types::get)
                .map(json -> json.get("type")
                        .get("name")
                        .asText())
                .collect(Collectors.toList());

//        Iterable<JsonNode> elements = () -> jsonNode.withArray("types").elements();
//        List<String> typeNames2 = StreamSupport.stream(elements.spliterator(), false)
//                .map(json -> json.get("type").get("name").asText())
//                .collect(Collectors.toList());

        System.out.println(typeNames2);


        JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("types");
        List<String> typeNames = IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .map(json -> json.getJSONObject("type")
                        .getString("name"))
                .collect(Collectors.toList());

        System.out.println(typeNames);
        System.exit(0);

        List<Integer>list1= Arrays.asList(10,20,30,40);
        List<Integer>list2=Arrays.asList(100,200,300,400);
        List<List<Integer>> bigList= new ArrayList<>();

        bigList.add(list1);
        bigList.add(list2);

        bigList.stream()
                .flatMap(Collection::stream)
                .forEach(x -> System.out.println(x));

        System.exit(0);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTime newTime = zonedDateTime.withMinute(0)
                .withSecond(0)
                .withNano(0);

        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").format(newTime));

        System.exit(0);
        System.out.println("Enter  message");
        Scanner e4 = new Scanner(System.in);
        String message  = e4.nextLine();
        System.out.println(message);

        System.exit(0);
        List<Integer> integers = Arrays.asList(-4, 5, -11);

        Integer integer = integers.stream()
                .map(Math::abs)
                .max(Integer::compareTo)
                .get();


        System.out.println(integer);

        String json = "{" +
                "  \"user\": {" +
                "    \"firstname\":\"Tom\"," +
                "    \"lastname\":\"Riddle\"" +
                "  }" +
                "}";


//        JsonParser parser = objectMapper.createParser(json);
//        parser.nextToken();


//        JsonNode jsonNode = objectMapper.readTree(json);
//        String content = jsonNode.elements().next().toString();
//        User user = objectMapper.readValue(content, User.class);
//        System.out.println(user);
//
//
//        int x = 6/0;

        String newJson = "{ \"horoscopes\": [" +
                "    {" +
                "      \"horoscopeId\": 1," +
                "      \"sunsign\": \"aquarius\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for aquarius\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 2," +
                "      \"sunsign\": \"pisces\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for pisces\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 3," +
                "      \"sunsign\": \"aries\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for aries\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 4," +
                "      \"sunsign\": \"taurus\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for taurus\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 5," +
                "      \"sunsign\": \"gemini\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for gemini\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 6," +
                "      \"sunsign\": \"cancer\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for cancer\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 7," +
                "      \"sunsign\": \"leo\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for leo\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 8," +
                "      \"sunsign\": \"virgo\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for virgo\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 9," +
                "      \"sunsign\": \"libra\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for libra\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 10," +
                "      \"sunsign\": \"scorpio\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for scorpio\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 11," +
                "      \"sunsign\": \"sagittarius\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for sagittarius\"," +
                "    }," +
                "    {" +
                "      \"horoscopeId\": 12," +
                "      \"sunsign\": \"capricorn\"," +
                "      \"month\": \"january\"," +
                "      \"horoscope\": \"I am the january horoscope for capricorn\"," +
                "    }" +
                " ]" +
                "}";




        try {
            JSONObject obj = new JSONObject(newJson);
            JSONArray horoscopeArray = obj.getJSONArray("horoscopes");

            List<String> horoscopesOfAries = IntStream.range(0, horoscopeArray.length())
                    .mapToObj(horoscopeArray::getJSONObject)
                    .filter(horoscopeJson -> horoscopeJson.getString("sunsign").equals("aries"))
                    .map(horoscopeJson -> horoscopeJson.getString("horoscope"))
                    .collect(Collectors.toList());

            System.out.println(horoscopesOfAries);

            /*for (int i = 0; i < horoscopeArray.length(); i++) {
                JSONObject horoscopeValues = horoscopeArray.getJSONObject(i);
                horoscope_al.add(horoscopeValues.getString("horoscope"));
                al_string += horoscope_al.get(i);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        JSONObject obj = new JSONObject(newJson);
//        JSONArray horoscopeArray = obj.getJSONArray("horoscopes");
//
//        List<String> horoscopesOfAries = IntStream.range(0, horoscopeArray.length())
//                .mapToObj(i -> horoscopeArray.getJSONObject(i))
//                .filter(horoscopeJson -> horoscopeJson.getString("sunsign").equals("aries"))
//                .map(horoscopeJson -> horoscopeJson.getString("horoscope"))
//                .collect(Collectors.toList());
//
//        System.out.println(horoscopesOfAries);

//        printTrees(6,3);
    }

//    xxx*xxx      *          *         *
//    x*****x    *****      *****     *****
//    *******

    private static void printTrees(int level, int treesCount) {
        int marginBetweenTrees = 3;

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < treesCount; i++) { // top of the tress (first line)
            addTreeLine(0, level, strBuilder);
            IntStream.range(0, marginBetweenTrees).forEach(value -> strBuilder.append(' '));
        }
        System.out.println(strBuilder.toString()); // printing first line

        for (int i = 1, sideAstrixCount = 2; i < level; i++, sideAstrixCount++) { // rest of the trees (other lines)
            int sideSpaces = (level - 1 - i);
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < treesCount; j++) {
                addTreeLine(sideAstrixCount, sideSpaces, stringBuilder);
                IntStream.range(0, marginBetweenTrees).forEach(value -> stringBuilder.append(' '));
            }

            System.out.println(stringBuilder.toString()); // printing current line line
        }
    }

    private static void addTreeLine(int sideAstrixCount, int sideSpaces, StringBuilder stringBuilder) {
        IntStream.range(0,sideSpaces).forEach(value -> stringBuilder.append(' '));
        IntStream.range(0, sideAstrixCount).forEach(value -> stringBuilder.append('*'));
        stringBuilder.append('*');
        IntStream.range(0, sideAstrixCount).forEach(value -> stringBuilder.append('*'));
        IntStream.range(0,sideSpaces).forEach(value -> stringBuilder.append(' '));
    }

    public static Double getMostFrequentElement(double[] a) {
        quickSort(a, 0, a.length - 1); // instead of Arrays.sort(a);
        double mostFrequentElementInSortedArray = getMostFrequentElementInSortedArray(a);
        System.out.println(mostFrequentElementInSortedArray);

        return mostFrequentElementInSortedArray;
    }

    private static double getMostFrequentElementInSortedArray(double[] sortedArr) {
        if (sortedArr.length == 0) {
            return Double.NaN;
        } else if (sortedArr.length == 1) {
            return sortedArr[0];
        }

        int maxCount = 1, currCount = 1;
        double result = Double.NaN, curr = sortedArr[0];

        for (int i = 1; i < sortedArr.length; i++) {
            if (curr == sortedArr[i]) {
                currCount++;

                if (currCount == maxCount) {
                    result = Double.NaN;
                } else if (currCount > maxCount) {
                    result = curr;
                    maxCount = currCount;
                }
            } else {
                curr = sortedArr[i];
                currCount = 1;
            }
        }

        return result;
    }

    public static void quickSort(double[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(double[] arr, int begin, int end) {
        double pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                double swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        double swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public static Double getPopularElement(double[] a) {
        int count = 1, tempCount;
        double popular = a[0];
        double temp;
        boolean isUnique = false;
        for (int i = 0; i < (a.length - 1); i++) {
            temp = a[i];
            tempCount = 0;
            for (int j = 1; j < a.length; j++) {
                if (temp == a[j])
                    tempCount++;
            }
            if (tempCount > count) {
                isUnique = true;
                popular = temp;
                count = tempCount;
            } else if (tempCount == count) {
                isUnique = false;
            }
        }
        return isUnique ? popular : Double.NaN;
    }

}
