import dao.DatabaseConnection;

public class DatabaseConnectionTest {
    
    public static void main(String[] args) {
        runAllTests();
    }
    
    public static void runAllTests() {
        System.out.println("=== Database Connection Tests ===");
        
        testEmailValidation();
        testPhoneValidation();
        testPriceValidation();
        testQuantityValidation();
        testEmptyStringValidation();
        
        System.out.println("=== All Tests Completed ===");
    }
    
    public static void testEmailValidation() {
        System.out.println("Testing Email Validation:");
        
        // Valid emails
        assert DatabaseConnection.isValidEmail("test@example.com") : "Valid email failed";
        assert DatabaseConnection.isValidEmail("user.name@domain.co.uk") : "Valid email with subdomain failed";
        
        // Invalid emails
        assert !DatabaseConnection.isValidEmail("invalid-email") : "Invalid email passed";
        assert !DatabaseConnection.isValidEmail("@domain.com") : "Email without username passed";
        assert !DatabaseConnection.isValidEmail("user@") : "Email without domain passed";
        
        System.out.println("✓ Email validation tests passed");
    }
    
    public static void testPhoneValidation() {
        System.out.println("Testing Phone Validation:");
        
        // Valid phones
        assert DatabaseConnection.isValidPhone("1234567890") : "10-digit phone failed";
        assert DatabaseConnection.isValidPhone("123456789012345") : "15-digit phone failed";
        
        // Invalid phones
        assert !DatabaseConnection.isValidPhone("123") : "Short phone passed";
        assert !DatabaseConnection.isValidPhone("12345678901234567890") : "Long phone passed";
        assert !DatabaseConnection.isValidPhone("12345abcde") : "Phone with letters passed";
        
        System.out.println("✓ Phone validation tests passed");
    }
    
    public static void testPriceValidation() {
        System.out.println("Testing Price Validation:");
        
        // Valid prices
        assert DatabaseConnection.isValidPrice(10.50) : "Positive price failed";
        assert DatabaseConnection.isValidPrice(0.01) : "Small positive price failed";
        
        // Invalid prices
        assert !DatabaseConnection.isValidPrice(0) : "Zero price passed";
        assert !DatabaseConnection.isValidPrice(-5.00) : "Negative price passed";
        
        System.out.println("✓ Price validation tests passed");
    }
    
    public static void testQuantityValidation() {
        System.out.println("Testing Quantity Validation:");
        
        // Valid quantities
        assert DatabaseConnection.isValidQuantity(1) : "Positive quantity failed";
        assert DatabaseConnection.isValidQuantity(100) : "Large quantity failed";
        
        // Invalid quantities
        assert !DatabaseConnection.isValidQuantity(0) : "Zero quantity passed";
        assert !DatabaseConnection.isValidQuantity(-1) : "Negative quantity passed";
        
        System.out.println("✓ Quantity validation tests passed");
    }
    
    public static void testEmptyStringValidation() {
        System.out.println("Testing Empty String Validation:");
        
        // Valid strings
        assert DatabaseConnection.isNotEmpty("test") : "Non-empty string failed";
        assert DatabaseConnection.isNotEmpty("  test  ") : "String with spaces failed";
        
        // Invalid strings
        assert !DatabaseConnection.isNotEmpty("") : "Empty string passed";
        assert !DatabaseConnection.isNotEmpty("   ") : "Whitespace-only string passed";
        assert !DatabaseConnection.isNotEmpty(null) : "Null string passed";
        
        System.out.println("✓ Empty string validation tests passed");
    }
}