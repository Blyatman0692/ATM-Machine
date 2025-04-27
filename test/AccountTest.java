import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account acct;

    @BeforeEach
    void setUp() {
        acct = new Account();
    }

    @Test
    void getCustomerNumberAndPinNumber_returnDefaultZero_initialAccount() {
        assertEquals(0, acct.getCustomerNumber());
        assertEquals(0, acct.getPinNumber());
    }

    @Test
    void setCustomerNumberAndPinNumber_updateValues_input12345And6789() {
        acct.setCustomerNumber(12345);
        acct.setPinNumber(6789);
        assertEquals(12345, acct.getCustomerNumber());
        assertEquals(6789, acct.getPinNumber());
    }

    @Test
    void getCheckingBalanceAndSavingBalance_returnZeroBalances_noTransactions() {
        assertEquals(0.0, acct.getCheckingBalance(), 1e-9);
        assertEquals(0.0, acct.getSavingBalance(), 1e-9);
    }

    @Test
    void calcCheckingDeposit_increaseBalance_input250_75() {
        double after = acct.calcCheckingDeposit(250.75);
        assertEquals(250.75, after, 1e-9);
        assertEquals(250.75, acct.getCheckingBalance(), 1e-9);
    }

    @Test
    void calcCheckingWithdraw_decreaseBalance_input50_25() {
        acct.calcCheckingDeposit(250.75);
        double after = acct.calcCheckingWithdraw(50.25);
        assertEquals(200.50, after, 1e-9);
        assertEquals(200.50, acct.getCheckingBalance(), 1e-9);
    }

    @Test
    void calcSavingDeposit_increaseBalance_input1000_00() {
        double after = acct.calcSavingDeposit(1000.00);
        assertEquals(1000.00, after, 1e-9);
        assertEquals(1000.00, acct.getSavingBalance(), 1e-9);
    }

    @Test
    void calcSavingWithdraw_decreaseBalance_input250_00() {
        acct.calcSavingDeposit(1000.00);
        double after = acct.calcSavingWithdraw(250.00);
        assertEquals(750.00, after, 1e-9);
        assertEquals(750.00, acct.getSavingBalance(), 1e-9);
    }

    @Test
    void calcCheckingWithdraw_allowNegative_input10_00() {
        double result = acct.calcCheckingWithdraw(10.00);
        assertEquals(-10.00, result, 1e-9);
        assertEquals(-10.00, acct.getCheckingBalance(), 1e-9);
    }

    @Test
    void calcSavingWithdraw_allowNegative_input5_00() {
        double result = acct.calcSavingWithdraw(5.00);
        assertEquals(-5.00, result, 1e-9);
        assertEquals(-5.00, acct.getSavingBalance(), 1e-9);
    }

    @Test
    void calcCheckTransfer_transfer200_00_fromCheckingToSaving() {
        acct.calcCheckingDeposit(500.00);
        acct.calcCheckTransfer(200.00);
        assertEquals(300.00, acct.getCheckingBalance(), 1e-9);
        assertEquals(200.00, acct.getSavingBalance(), 1e-9);
    }

    @Test
    void calcSavingTransfer_transfer150_00_fromSavingToChecking() {
        acct.calcSavingDeposit(400.00);
        acct.calcSavingTransfer(150.00);
        assertEquals(150.00, acct.getCheckingBalance(), 1e-9);
        assertEquals(250.00, acct.getSavingBalance(), 1e-9);
    }

    @Test
    void multipleOperationsSequence_updateBalances_depositsWithdrawsAndTransfers() {
        acct.calcCheckingDeposit(1000.00);
        acct.calcSavingDeposit(500.00);
        acct.calcCheckingWithdraw(100.00);
        acct.calcSavingTransfer(200.00);
        assertEquals(1100.00, acct.getCheckingBalance(), 1e-9);
        assertEquals(300.00,  acct.getSavingBalance(),   1e-9);
    }
}
