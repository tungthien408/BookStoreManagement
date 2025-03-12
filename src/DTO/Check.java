package DTO;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

// lỗi:
// 1. khi 1 chức vụ nào đó chỉ có 1 nhân viên nắm giữ, nhưng phần mềm vẫn có thể xóa được => thực tế không thể xóa chức vụ/nhân viên hoặc sửa đổi chức vụ của nhân viên đó được
// 2. khi nhập id của một người/quyển sách nào đó để thực hiện các thao tác, bắt buộc phải gõ đúng kí tự in hoa/in thường thì phần mềm mới có thể tìm được quyển sách => gây bất tiện cho người dùng
// 3. nhập 1 chuỗi có độ dài lớn hơn 14 kí tự thì sẽ bị báo lỗi, phần mềm không nhận dữ liệu đó => không hợp lý
// 4. không ngặn chặn người dùng khi nhập sđt đã có người đăng ký => không hợp lý
// 5. nhập email không đúng định dạng, phần mềm vẫn chấp nhận
// 6. không ràng buộc định dạng số điện thoại
// 7. không ràng buộc định dạng cccd
// 8. không kiểm tra năm nhuận
// 9. chưa có chỗ để bán sách
// 10. chưa thiết kế hợp lí ở chỗ nhập sách (lựa chọn lứa tuổi, nhập mã tác giả)
// 11. đọc load file chưa kĩ (tui xóa hết dữ liệu của các bảng, chừa mỗi admin, khi tắt chương trình mở lại, các dữ liệu vẫn hiển thị lên bình thường)


// những phần tui nghĩ nên xem xét lại
// 1. phần mềm này quản lý nhân viên bằng username, không phải bằng ID, liệu có dễ dàng hơn trong việc quản lý hong


public class Check implements Serializable {
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_YELLOW = "\u001B[33m";

    // Dùng để lặp lại chuỗi str với n lần
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
                Check.printError("Ban chi duoc phep nhap so");
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
                    Check.printError("Khong chap nhan so <= 0");
                else
                    return input;
            } catch (NumberFormatException e) {
                Check.printError("Ban chi duoc phep nhap so");
            }
        }
    }

    public static int takeSoLuongCanTao(String nhapGi) {
        int num;
        while (true) {
            num = Check.takeIntegerInput(nhapGi);
            if (num > 5) {
                Check.printMessage("So luong cua ban hoi nhieu");
                System.out.println("1. Tiep tuc");
                System.out.println("2. Nhap lai");
                if (Check.takeInputChoice(1, 2) == 1)
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
                    Check.printError("Ban chua nhap gi het");
                else if (input.length() > 14)
                    Check.printError("Chieu dai chuoi vuot qua 14");
                else
                    return input;
            } catch (NoSuchElementException e) {
                Check.printError("Ban chua nhap gi het");
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
                System.out.print("Nhap lua chon: ");
            else if (sln < 2)
                System.out.print("Nhap lai lua chon: ");
            else
                System.out.print("Hay nhin lai lua chon roi nhap: ");
            choice = sc.nextLine();
            try {
                int num = Integer.parseInt(choice);
                if (num >= min && num <= max)
                    return num;
                else {
                    Check.printError("Lua chon khong hop le");
                    sln++;
                }
            } catch (NumberFormatException e) {
                Check.printError("Ban chi duoc phep nhap so");
                sln++;
            }
        }
    }

    // Nhận input ngày tháng
    public static String takeDateInput(String nhapGi) {
        String date;
        while (true) {
            date = takeStringInput(nhapGi);
            if (checkDate(date))
                return date;
            else
                Check.printError("Ngay thang khong hop le");
        }
    }

    // Nhận input số điện thoại
    public static String takePhoneNumberInput(String nhapGi) {
        String phoneNumber;
        while (true) {
            phoneNumber = takeStringInput(nhapGi);
            if (phoneNumber.matches("\\d+"))
                return phoneNumber;
            else
                Check.printError("So dien thoai khong hop le");
        }
    }

    // Lưu object vào file
    public static void save(Object obj, String fileName) {
        try {
            Check.printMessage("Save file thanh cong");
            FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutputStream oStream = new ObjectOutputStream(f);
            oStream.writeObject(obj);
            oStream.close();
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e);
        }
    }

    // Load object từ file
    public static Object load(Object obj, String fileName) {
        try {
            FileInputStream f = new FileInputStream(fileName);
            ObjectInputStream inStream = new ObjectInputStream(f);
            obj = inStream.readObject();
            inStream.close();
            Check.printMessage("Load file len co may thoi gian thanh cong");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            Check.printError("Co may thoi gian co van de");
            return null;
        }
    }

    // Lấy ngày hiện tại
    public static String getDateNow() {
        LocalDate date = LocalDate.now();
        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
    }

    // Dùng để clear console
    public static void clearScreen() {
        Check.printMessage("Nhap bat ky de tiep tuc");
        Scanner cn = new Scanner(System.in);
        cn.nextLine();
        System.out.printf("%5s", repeatStr("\n", 5));
    }

    // Hiển thị tin nhắn với text màu xanh
    public static void printMessage(String message) {
        System.out.printf("%30s " + TEXT_GREEN + " *** %s *** " + TEXT_RESET + " %30s%n",
                Check.repeatStr(" ", 30), message, Check.repeatStr(" ", 30));
    }

    // Hiển thị lỗi với text màu đỏ
    public static void printError(String message) {
        System.out.printf("%30s " + TEXT_RED + " *** %s *** " + TEXT_RESET + " %30s%n",
                Check.repeatStr(" ", 30), message, Check.repeatStr(" ", 30));
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
            if (month == 2 && day > 29)
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

    public static boolean kiemTraKhoangThoiGian(String from, String between, String to) {
        Date dateFrom;
        Date dateTo;
        Date dateBetween;
        try {
            dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(from);
            dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(to);
            dateBetween = new SimpleDateFormat("dd/MM/yyyy").parse(between);
        } catch (ParseException e) {
            return false;
        }
        return dateBetween.after(dateFrom) && dateBetween.before(dateTo);
    }

    public static boolean subStrInStrIgnoreCase(String str, String subStr) {
        return str.toLowerCase(Locale.ROOT).contains(subStr.toLowerCase(Locale.ROOT));
    }
}