package io;
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG


//         ┌─┐       ┌─┐
//      ┌──┘ ┴───────┘ ┴──┐
//      │                 │
//      │       ───       │
//      │  ─┬┘       └┬─  │
//      │                 │
//      │       ─┴─       │
//      │                 │
//      └───┐         ┌───┘
//          │         │
//          │         │
//          │         │
//          │         └──────────────┐
//          │                        │
//          │                        ├─┐
//          │                        ┌─┘
//          │                        │
//          └─┐  ┐  ┌───────┬──┐  ┌──┘
//            │ ─┤ ─┤       │ ─┤ ─┤
//            └──┴──┘       └──┴──┘
//                神兽保佑
//                代码无BUG!


//
//  ██████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
// ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
// ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
// ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
// ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
//  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
//  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
//  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
//           ░     ░ ░      ░  ░
//                 ░

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @date 2018/1/2 16:58
 * Description
 */
public class ImageTest {

    public static final int RBG_WHITE = new Color(255, 255, 255).getRGB();
    public static final int RBG_BLACK = new Color(0, 0, 0).getRGB();

    public static void main(String[] args) throws IOException {

        String path = new ImageTest().getCurrFilePath();
        System.out.println(path);

        String fileName = "finddiff.png";
        File fromFile = Paths.get(path + fileName).toFile();
        if (!fromFile.exists()) {
            System.out.println("file not exist.");
        }
        String toFileName = "dest.jpg";
        File toFile = Paths.get(path + toFileName).toFile();
        String grayFileName = "gray.jpg";
        File grayFile = Paths.get(path + grayFileName).toFile();
        if (!grayFile.exists()) {
            grayFile.createNewFile();
        }

        BufferedImage bufferedImage = ImageIO.read(fromFile);
        // 获取当前图片的高,宽,ARGB
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();
        System.out.println(String.format("height: %d, width: %d", h, w));
        int arr[][] = new int[w][h];

        // 获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageRgb(bufferedImage.getRGB(i, j));//该点的灰度值
            }

        }

        // 二值化
        // 构造一个类型为预定义图像类型之一的 BufferedImage，TYPE_BYTE_BINARY（表示一个不透明的以字节打包的 1、2 或 4 位图像。）
        BufferedImage bufImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        int FZ = 130;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (getGray(arr, i, j, w, h) > FZ) {
                    bufImg.setRGB(i, j, RBG_WHITE);
                } else {
                    bufImg.setRGB(i, j, RBG_BLACK);
                }
            }
        }
        ImageIO.write(bufImg, "jpg", toFile);

        // 灰度
        // 构造一个类型为预定义图像类型之一的 BufferedImage，TYPE_BYTE_BINARY（表示一个不透明的以字节打包的 1、2 或 4 位图像。）
        BufferedImage bufImg1 = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                bufImg1.setRGB(i, j, bufferedImage.getRGB(i, j));
            }
        }
        ImageIO.write(bufImg1, "jpg", grayFile);

        /*int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        System.out.println(width);
        System.out.println(height);

        int rgb = bufferedImage.getRGB(20, 20);
        System.out.println(rgb);
        Color color = new Color(255, 255, 255);
        bufferedImage.setRGB(20, 20, color.getRGB());

        // new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_USHORT_GRAY);

        ImageIO.write(bufferedImage, "jpg", toFile);*/
    }


    /**
     * 获取当前类的绝对路径
     * @return
     */
    public String getCurrFilePath() {
        URL url = getClass().getResource("");
        String path = url.getPath();
        String osName = System.getProperty("os.name", "");
        path = path.replace("target/classes/", "src/main/java/");
        if (osName.contains("Win")) {
            if ('/' == path.charAt(0)) {
                path = path.replaceFirst("/", "");
            }
        }
        System.out.println("Path: " + path);
        return path;
    }

    private static int getImageRgb(int i) {
        // 将十进制的颜色值转为十六进制
        String argb = Integer.toHexString(i);
        // argb分别代表透明,红,绿,蓝 分别占16进制2位
        // 后面参数为使用进制
        int r = Integer.parseInt(argb.substring(2, 4), 16);
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6, 8), 16);
        int result = (r + g + b) / 3;
        return result;
    }


    // 自己加周围8个灰度值再除以9，算出其相对灰度值
    public static int getGray(int gray[][], int x, int y, int w, int h) {
        int rs = gray[x][y]
                + (x == 0 ? 255 : gray[x - 1][y])
                + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
                + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1])
                + (y == 0 ? 255 : gray[x][y - 1])
                + (y == h - 1 ? 255 : gray[x][y + 1])
                + (x == w - 1 ? 255 : gray[x + 1][y])
                + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
                + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
        return rs / 9;
    }
}