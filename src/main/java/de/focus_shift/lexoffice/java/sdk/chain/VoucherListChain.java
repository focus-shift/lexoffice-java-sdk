package de.focus_shift.lexoffice.java.sdk.chain;

import com.google.common.base.Joiner;
import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.RequestContext;
import de.focus_shift.lexoffice.java.sdk.model.Page;
import de.focus_shift.lexoffice.java.sdk.model.Voucher;
import de.focus_shift.lexoffice.java.sdk.model.VoucherStatus;
import de.focus_shift.lexoffice.java.sdk.model.VoucherType;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class VoucherListChain extends ExecutableRequestChain {

    private static final ParameterizedTypeReference<Page<Voucher>> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private boolean voucherStatusAdded = false;
    private boolean voucherTypeAdded = false;

    public VoucherListChain(RequestContext context) {
        super(context, "/voucherlist");
    }

    /**
     * Pages are zero indexed, thus providing 0 for page will return the first page.
     */
    public VoucherListChain page(int page) {
        super.getUriBuilder()
                .addParameter("page", String.valueOf(page));
        return this;
    }

    /**
     * Default page size is set to 25 but can be increased up to 100/250 (depends on the used endpoint).
     */
    public VoucherListChain pageSize(int pageSize) {
        super.getUriBuilder()
                .addParameter("size", String.valueOf(pageSize));
        return this;
    }

    /**
     * Property significant to order of the contents. Possible values are voucherDate, voucherNumber, and updatedDate. The sort direction can be added separated by a comma. Possible values for the direction are ASC or DESC.
     */
    public VoucherListChain sort(String sort) {
        super.getUriBuilder()
                .addParameter("sort", sort);
        return this;
    }

    public VoucherListChain sortByVoucherDate(boolean asc) {
        super.getUriBuilder()
                .addParameter("sort", String.format("voucherDate,%s", asc ? "ASC" : "DESC"));
        return this;
    }

    public VoucherListChain sortByVoucherNumber(boolean asc) {
        super.getUriBuilder()
                .addParameter("sort", String.format("voucherNumber,%s", asc ? "ASC" : "DESC"));
        return this;
    }

    public VoucherListChain sortByUpdatedDate(boolean asc) {
        super.getUriBuilder()
                .addParameter("sort", String.format("updatedDate,%s", asc ? "ASC" : "DESC"));
        return this;
    }

    public VoucherListChain archived(boolean archived) {
        super.getUriBuilder()
                .addParameter("archived", String.valueOf(archived));
        return this;
    }

    public VoucherListChain voucherType(VoucherType... voucherType) {
        super.getUriBuilder()
                .addParameter("voucherType", Joiner.on(",").join(Arrays.asList(voucherType).stream().map(VoucherType::getValue).collect(Collectors.toList())));
        voucherTypeAdded = true;
        return this;
    }

    public VoucherListChain voucherStatus(VoucherStatus... voucherStatus) {
        super.getUriBuilder()
                .addParameter("voucherStatus", Joiner.on(",").join(Arrays.asList(voucherStatus).stream().map(VoucherStatus::getValue).collect(Collectors.toList())));
        voucherStatusAdded = true;
        return this;
    }

    public VoucherListChain voucherNumber(String voucherNumber) {
        super.getUriBuilder()
                .addParameter("voucherNumber", voucherNumber);
        return this;
    }

    public VoucherListChain voucherDate(Date voucherDate) {
        super.getUriBuilder()
                .addParameter("voucherDate", LexofficeApi.DATE_TIME_FORMAT.format(voucherDate));
        return this;
    }
    public VoucherListChain createdDate(Date createdDate) {
        super.getUriBuilder()
                .addParameter("createdDate", LexofficeApi.DATE_TIME_FORMAT.format(createdDate));
        return this;
    }

    public VoucherListChain updatedDate(Date updatedDate) {
        super.getUriBuilder()
                .addParameter("updatedDate", LexofficeApi.DATE_TIME_FORMAT.format(updatedDate));
        return this;
    }

    public VoucherListChain dueDate(Date dueDate) {
        super.getUriBuilder()
                .addParameter("dueDate", LexofficeApi.DATE_TIME_FORMAT.format(dueDate));
        return this;
    }

    public VoucherListChain contactName(String contactName) {
        super.getUriBuilder()
                .addParameter("contactName", contactName);
        return this;
    }

    public VoucherListChain contactId(String contactId) {
        super.getUriBuilder()
                .addParameter("contactId", contactId);
        return this;
    }

    @SneakyThrows
    public Page<Voucher> get() {
        if (!voucherTypeAdded) {
            voucherType(VoucherType.values());
        }
        if (!voucherStatusAdded) {
            voucherStatus(VoucherStatus.valuesNonExclusive());
        }
        return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
    }

}
