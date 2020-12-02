package com.cxy.lambda;

import com.cxy.exception.MyException;
import com.cxy.lambda.entity.Album;
import com.cxy.lambda.entity.Artist;
import com.cxy.lambda.entity.Track;
import com.cxy.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


/**
 * @author ：陈翔宇
 * @date ：2020/11/30 16:20
 * @description：
 * @modified By：
 * @version: $
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class LambdaStudy {

    @Autowired
    private IUserService userService;

    @Test
    public void test1() throws MyException {
        List<String> collected = Stream.of("a", "b", "c")
                .collect(toList());
        try {
            assertEquals(Arrays.asList("a", "b", "c1"), collected);
        } catch (MyException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("数组校验通过");
    }

    @Test
    public void map() {
        List<String> collected = Stream.of("a", "b", "hello")
                .map(String::toUpperCase)
                .collect(toList());
        String[] strs = new String[]{"A", "B", "HELLO"};
        List<String> strings = Arrays.asList(strs);
        try {
            assertEquals(strings, collected);
        } catch (MyException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void filter() {
        List<String> beginningWithNumbers
                = Stream.of("a", "1abc", "abc1")
                .filter(value -> isDigit(value.charAt(0)))
                .collect(toList());
        try {
            assertEquals(asList("1abc"), beginningWithNumbers);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void flatmap() throws MyException {
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(Collection::stream)
                .collect(toList());
        assertEqualsInt(asList(1, 2, 3, 4), together);
    }

    @Test
    public void min() throws MyException {
        List<Track> tracks = asList(new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.stream()
                .min(Comparator.comparing(Track::getLength))
                .get();
        Track shortestTrack2 = tracks.stream()
                .max(Comparator.comparing(Track::getLength))
                .get();
        assertEquals(tracks.get(1), shortestTrack);

    }

    @Test
    public void reduce() throws MyException {
        int min = Stream.of(1, 2, 3)
                .reduce(10, (acc, element) -> {
                    if (acc > element) {
                        acc = element;
                    }
                    return acc;
                });

        int max = Stream.of(1, 2, 3)
                .reduce(10, (acc, element) -> {
                    if (acc < element) {
                        acc = element;
                    }
                    return acc;
                });
        assertEquals(6, min);
    }

    @Test
    public void getNationalityByAlbum() {
        Album album = new Album();
        Set<String> strings = album.getMusicianList().stream()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(Artist::getNationality)
                .collect(toSet());
    }


    @Test
    public void testFindLongTracks() {
        ArrayList<Album> albums = new ArrayList<>();
        Album album = new Album();
        album.setName("cxy");
        albums.add(album);
        Album album1 = new Album();
        album1.setName("zj");
        albums.add(album1);
        findLongTracks(albums, 50);
    }

    @Test
    public void var() {
        ArrayList<Album> albums = new ArrayList<>();
        albums.add(new Album());
        AtomicReference<Album> album = new AtomicReference<>(new Album());

        albums.stream()
                .filter(album1 -> {
                    album.set(album1);
                    return album1.getTrackList().size() > 0;
                })
                .collect(toSet());
    }

    /**
     * 找到多线程可共享的变量，nice呀，哈哈哈
     * ThreadLocal 多线程中 各自独一份 什么鬼玩意，没哈卵用
     */
    @Test
    public void atomicRef() {
        AtomicReference<Integer> atomicReference = new AtomicReference<>();
        atomicReference.set(1);

        Thread thread = new Thread(() -> {
            Integer integer = atomicReference.updateAndGet(v -> v + 1);
            System.out.println(integer);
        });
        Thread thread2 = new Thread(() -> {
            Integer integer = atomicReference.updateAndGet(v -> v + 1);
            System.out.println(integer);
        });
        thread.start();
        thread2.start();
    }

    @Test
    public void addUp() {
        List<Integer> integers = asList(1, 2, 3, 4);
        Integer integer = addUp(integers.stream());
        System.out.println(integer);
    }

    @Test
    public void nameAndNationalityTest(){
        ArrayList<Artist> artists = new ArrayList<>();
        Artist artist = new Artist();
        artist.setName("陈翔宇");
        artist.setNationality("中国");
        Artist artist1 = new Artist();
        artist1.setName("张佳");
        artist1.setNationality("中国");
        artists.add(artist);
        artists.add(artist1);

        Set<String> strings = nameAndNationality(artists);
        System.out.println(strings);
    }

    // . 编写一个函数，接受艺术家列表作为参数，返回一个字符串列表，其中包含艺术家的
    //姓名和国籍；
    public Set<String> nameAndNationality(List<Artist> list) {
        return list.stream()
                .map(artist -> artist.getName() + ": " + artist.getNationality())
                .collect(toSet());
    }

    /**
     * 求和
     *
     * @param numbers
     * @return
     */
    public Integer addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, Integer::sum);
    }

    // Question 6
    public static int countLowercaseLetters(String string) {
        return (int) string.chars()
                .filter(Character::isLowerCase)
                .count();
    }

    // Question 7
    public static Optional<String> mostLowercaseString(List<String> strings) {
        return strings.stream()
                .max(Comparator.comparing(LambdaStudy::countLowercaseLetters));
    }

    public  void reduceReplaceMap(){

    }

    /**
     * 长专辑
     *
     * @param albums：长度
     * @return
     */
    public Set<String> findLongTracks(List<Album> albums, Integer length) {
        return albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > length)
                .map(Track::getName)
                .collect(toSet());
    }

    // 练习
    public Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }

        albums.stream()
                .forEach(album -> {
                    album.getTrackList()
                            .stream().forEach(track -> {
                        if (track.getLength() > 60) {
                            String name = track.getName();
                            trackNames.add(name);
                        }
                    });
                });
        Set<String> names = albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > 60)
                .map(Track::getName)
                .collect(toSet());
        return trackNames;
    }


    private List<String> asList(String... nums) {
        return Arrays.asList(nums);
    }

    private List<Integer> asList(Integer... nums) {
        return Arrays.asList(nums);
    }

    private List<Track> asList(Track... tracks) {
        return Arrays.asList(tracks);
    }

    private void assertEquals(List<String> asList, List<String> collected) throws MyException {
        if (asList.size() != collected.size()) {
            throw new MyException("数组不一致");
        }
        for (int i = 0; i < asList.size(); i++) {
            if (!asList.get(i).equals(collected.get(i))) {
                throw new MyException("数组不一致");
            }
        }
    }

    private void assertEquals(Track track1, Track track2) throws MyException {
        if (track1 != track2) {
            throw new MyException("对象不一致");
        }
    }

    private void assertEquals(int var1, int var2) throws MyException {
        if (var1 != var2) {
            throw new MyException("不一致");
        }
    }


    private void assertEqualsInt(List<Integer> asList, List<Integer> collected) throws MyException {
        if (asList.size() != collected.size()) {
            throw new MyException("数组不一致");
        }
        for (int i = 0; i < asList.size(); i++) {
            if (!asList.get(i).equals(collected.get(i))) {
                throw new MyException("数组不一致");
            }
        }
    }

}

