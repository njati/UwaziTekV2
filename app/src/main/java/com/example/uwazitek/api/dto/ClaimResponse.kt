import com.google.gson.annotations.SerializedName

data class ClaimResponse(
    @SerializedName("approval_remarks") val approvalRemarks: String,
    @SerializedName("claim_id") val claimId: Int,
    @SerializedName("claim_narration") val claimNarration: String,
    @SerializedName("claim_reference") val claimReference: String,
    @SerializedName("date_approved") val dateApproved: String,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("hospital_id") val hospitalId: Int,
    @SerializedName("hospital_name") val hospitalName: String,
    @SerializedName("invoice_amount") val invoiceAmount: String,
    @SerializedName("invoice_number") val invoiceNumber: String,
    @SerializedName("maximum_cost") val maximumCost: String,
    @SerializedName("min_cost") val minimumCost: String,
    @SerializedName("policy_number") val policyNumber: String,
    @SerializedName("risk_classification") val riskClassification: String,
    @SerializedName("status_code") val statusCode: String,
    @SerializedName("status_description") val statusDescription: String,
    @SerializedName("treatment_id") val treatmentId: Int
)
