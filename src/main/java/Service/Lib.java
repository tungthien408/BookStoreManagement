package Service;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Lib implements Serializable {
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_YELLOW = "\u001B[33m";

    // Dùng là lặp lại chuỗi str với n lần
    static String repeatStr(String str, int n) {
        return String.join("", Collections.nCopies(n, str));
    }

    // Nhận input float
    public static float takeFloatInput(String nhapGi) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(nhapGi);
            try {
                return Float.parseFloat(sc.nextLine());
            } catch (NumberFormatException e) {
                Lib.printError("Bạn chỉ được phép nhập số");
            }
        }
    }

    // Nhận input int
    public static int takeIntegerInput(String nhapGi) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(nhapGi);
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input <= 0)
                    Lib.printError("Không chấp nhận số <= 0");
                else
                    return input;
            } catch (NumberFormatException e) {
                Lib.printError("Bạn chỉ được phép nhập số");
            }
        }
    }

    public static int takeSoLuongCanTao(String nhapGi) {
        int num;
        while (true) {
            num = Lib.takeIntegerInput(nhapGi);
            if (num > 5) {
                Lib.printMessage("Số lượng của bạn hơi nhiều");
                System.out.println("1. Tiếp tục");
                System.out.println("2. Nhập lại");
                if (Lib.takeInputChoice(1, 2) == 1)
                    return num;
                else
                    continue;
            }
            break;
        }
        return num;
    }

    // Nhận input String
    public static String takeStringInput(String nhapGi) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(nhapGi);
            try {
                String input = sc.nextLine();
                if (input.isEmpty())
                    Lib.printError("Bạn chưa có nhập gì hết");
                else if (input.length() > 14)
                    Lib.printError("Chiều dài chuỗi vượt quá 14");
                else
                    return input;
            } catch (NoSuchElementException e) {
                Lib.printError("Bạn chưa có nhập gì hết");
            }
        }
    }

    // Nhận lựa chọn với khoảng từ min tới max [min, max]
    public static int takeInputChoice(int min, int max) {
        String choice;
        Scanner sc = new Scanner(System.in);
        int sln = 0;
        while (true) {
            if (sln == 0)
                System.out.print("Nhập lựa chọn: ");
            else if (sln < 2)
                System.out.print("Nhập lại lựa chọn: ");
            else
                System.out.print("Hãy nhìn lại lựa chọn rồi nhập -_- : ");
            choice = sc.nextLine();
            try {
                int result = Integer.parseInt(choice);
                if (result >= min && result <= max)
                    return result;
                printError("Lựa chọn của bạn không đúng lắm");
            } catch (NumberFormatException e) {
                Lib.printError("Bạn chỉ được phép nhập số");
            }
            sln++;
        }
    }

    public static String takeDateInput(String nhapGi) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(nhapGi);
            try {
                String input = sc.nextLine();
                if (input.isEmpty())
                    Lib.printError("Bạn chưa có nhập gì hết");
                else if (input.length() > 14)
                    Lib.printError("Chiều dài chuỗi vượt quá 14");
                else if (!checkDate(input))
                    Lib.printError("Ngày không đúng định dạng");
                else
                    return input;
            } catch (NoSuchElementException e) {
                Lib.printError("Bạn chưa có nhập gì hết");
            }
        }
    }

    public static String takePhoneNumberInput(String nhapGi) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(nhapGi);
            try {
                String input = sc.nextLine();
                int check = Integer.parseInt(input);
                if (input.isEmpty())
                    Lib.printError("Bạn chưa có nhập gì hết");
                else if (input.length() > 14)
                    Lib.printError("Chiều dài chuỗi vượt quá 14");
                else
                    return input;
            } catch (NoSuchElementException e) {
                Lib.printError("Bạn chưa có nhập gì hết");
            } catch (NumberFormatException e) {
                Lib.printError("Điện thoại chỉ chấp nhận số");
            }
        }
    }

    // Load file tham số obj là obj là cần load. Trả về null nếu lỗi và obj nếu đúng
    public static Object load(Object obj, String filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException | NullPointerException | NoClassDefFoundError IOE) {
            printError("Load " + filename + " thất bại => tạo dữ liệu mặc định");
            return null;
        }
        printMessage("Load " + filename + " thành công");
        return obj;
    }

    // Save file tham số obj là obj cần save
    public static void save(Object obj, String filename) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(obj);
            Lib.printMessage("Save " + filename + " thành công");
        } catch (IOException e) {
            e.printStackTrace();
            Lib.printError("Save " + filename + " thất bại");
        }
    }

    // Trả về ngày tháng năm hiện tại
    
    public static Date getDateNow() {
        return Date.valueOf(LocalDate.now()); // Trả về ngày hiện tại dạng SQL Date
    }


    // Dùng để clear console
    public static void clearScreen() {
        Lib.printMessage("Nhập bất kỳ để tiếp tục");
        Scanner cn = new Scanner(System.in);
        cn.nextLine();
        System.out.printf("%5s", repeatStr("\n", 5));
    }

    // Hiển thị tin nhắn với text màu xanh
    public static void printMessage(String message) {
        System.out.printf("%30s " + TEXT_GREEN + " *** %s *** " + TEXT_RESET + " %30s%n", Lib.repeatStr(" ", 30),
                message, Lib.repeatStr(" ", 30));
    }

    // Hiển thị lỗi với text màu đỏ
    public static void printError(String message) {
        System.out.printf("%30s " + TEXT_RED + " *** %s *** " + TEXT_RESET + " %30s%n", Lib.repeatStr(" ", 30), message,
                Lib.repeatStr(" ", 30));
    }

    public static boolean checkDate(String date) {

        try {
            String[] part = date.split("/");
            int day = Integer.parseInt(part[0]);
            int month = Integer.parseInt(part[1]);
            int year = Integer.parseInt(part[2]);
            if (day <= 0 || day > 31)
                return false;
            if (month <= 0 || month >= 13)
                return false;
            return year > 0 && year < 10000;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static String toBlueText(String text) {
        return TEXT_BLUE + text + TEXT_RESET;
    }

    public static String toGreenText(String text) {
        return TEXT_GREEN + text + TEXT_RESET;
    }

    public static String toYellowText(String text) {
        return TEXT_YELLOW + text + TEXT_RESET;
    }


    // public static boolean kiemTraKhoangThoiGian(String from, String between, String to) {
    //     Date dateFrom;
    //     Date dateTo;
    //     Date dateBetween;
    //     try {
    //         dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(from);
    //         dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(to);
    //         dateBetween = new SimpleDateFormat("dd/MM/yyyy").parse(between);
    //     } catch (ParseException e) {
    //         return false;
    //     }

    //     return dateBetween.after(dateFrom) && dateBetween.before(dateTo);
    // }

    public static boolean subStrInStrIgnoreCase(String str, String subStr) {
        return str.toLowerCase(Locale.ROOT).contains(subStr.toLowerCase(Locale.ROOT));
    }

    public static void main(String[] args) {

    }
}
