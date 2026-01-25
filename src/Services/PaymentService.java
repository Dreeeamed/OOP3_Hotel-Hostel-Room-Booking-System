package Services;

import Entities.Payment;
import Repositories.IPaymentRepository;

import java.util.List;

public class PaymentService {
    private final IPaymentRepository paymentRepo;

    public PaymentService(IPaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public void makePayment(int reservationId, double amount) {
        if (amount <= 0) {
            System.out.println("You must enter a positive amount :thumb_up:");
            return;
        }
        Payment payment = new Payment(reservationId, amount);
        paymentRepo.addPayment(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.getAllPayments();
    }
}