<%@page import="in.com.practice.ctl.ConsumerCtl"%>
<%@page import="in.com.practice.ctl.ORSView"%>
<%@page import="in.com.practice.util.DataUtility"%>
<%@page import="in.com.practice.util.ServletUtility"%>
<html>
<head>
    <title>Consumer</title>
</head>
<body>

<form action="<%=ORSView.CONSUMER_CTL%>" method="post">

    <%@ include file="Header.jsp"%>

    <jsp:useBean id="bean" class="in.com.practice.bean.ConsumerBean" scope="request"></jsp:useBean>

    <div align="center">

        <h1 style="color: navy">
            <%
            if (bean != null && bean.getConsumerId() != null && bean.getConsumerId() > 0) {
            %>
                Update Consumer
            <%
                } else {
            %>
                Add Consumer
            <%
                }
            %>
        </h1>

        <!-- SUCCESS / ERROR -->
        <h3 style="color: green;">
            <%=ServletUtility.getSuccessMessage(request)%>
        </h3>

        <h3 style="color: red;">
            <%=ServletUtility.getErrorMessage(request)%>
        </h3>

        <!-- HIDDEN FIELDS -->
        <input type="hidden" name="id" value="<%=bean.getConsumerId()%>">
        <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"
               value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime"
               value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <table>

            <!-- CONSUMER CODE -->
            <tr>
                <th align="left">Consumer Code<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="consumerCode"
                        placeholder="Enter Consumer Code"
                        value="<%=DataUtility.getStringData(bean.getConsumerCode())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("consumerCode", request)%>
                    </font>
                </td>
            </tr>

            <!-- GROUP -->
            <tr>
                <th align="left">Group<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="consumerGroup"
                        placeholder="Enter Group"
                        value="<%=DataUtility.getStringData(bean.getConsumerGroup())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("consumerGroup", request)%>
                    </font>
                </td>
            </tr>

            <!-- TOPIC NAME -->
            <tr>
                <th align="left">Topic Name<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="topicName"
                        placeholder="Enter Topic Name"
                        value="<%=DataUtility.getStringData(bean.getTopicName())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("topicName", request)%>
                    </font>
                </td>
            </tr>

            <!-- STATUS -->
            <tr>
                <th align="left">Status<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="status"
                        placeholder="ACTIVE / INACTIVE"
                        value="<%=DataUtility.getStringData(bean.getStatus())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("status", request)%>
                    </font>
                </td>
            </tr>

            <tr><th></th><td></td></tr>

            <!-- BUTTONS -->
            <tr>
                <th></th>

                <%
                    if (bean != null && bean.getConsumerId() > 0) {
                %>
                    <td colspan="2">
                        <input type="submit" name="operation" value="<%=ConsumerCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=ConsumerCtl.OP_CANCEL%>">
                    </td>
                <%
                    } else {
                %>
                    <td colspan="2">
                        <input type="submit" name="operation" value="<%=ConsumerCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=ConsumerCtl.OP_RESET%>">
                    </td>
                <%
                    }
                %>
            </tr>

        </table>

    </div>

</form>

</body>
</html>