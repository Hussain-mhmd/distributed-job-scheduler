import "./MetricCard.css";

function MetricCard({ title, value, color }) {

    return (
        <div
            className="metric-card"
            style={{
                borderTop: `5px solid ${color}`
            }}
        >
            <p className="metric-title">
                {title}
            </p>

            <h2 className="metric-value">
                {value}
            </h2>
        </div>
    );
}

export default MetricCard;