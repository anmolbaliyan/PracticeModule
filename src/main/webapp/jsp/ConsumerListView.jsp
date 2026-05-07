<%@page import="in.com.practice.ctl.ConsumerListCtl"%>
<%@page import="in.com.practice.bean.ConsumerBean"%>
<%@page import="in.com.practice.util.DataUtility"%>
<%@page import="in.com.practice.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Consumer List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.com.practice.bean.ConsumerBean" scope="request"></jsp:useBean>

<div align="center">

    <h1 style="color: navy;">Consumer List</h1>

    <!-- MESSAGES -->
    <h3 style="color: red;"><%=ServletUtility.getErrorMessage(request)%></h3>
    <h3 style="color: green;"><%=ServletUtility.getSuccessMessage(request)%></h3>

    <form action="<%=ORSView.CONSUMER_LIST_CTL%>" method="post">

        <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

            @SuppressWarnings("unchecked")
            List<ConsumerBean> list = (List<ConsumerBean>) ServletUtility.getList(request);

            Iterator<ConsumerBean> it = list.iterator();

            if (list.size() != 0) {
        %>

        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">

        <!-- SEARCH -->
        <table style="width: 100%">
            <tr>
                <td align="center">

                    <label><b>Code :</b></label>
                    <input type="text" name="consumerCode"
                        value="<%=ServletUtility.getParameter("consumerCode", request)%>">

                    &emsp;

                    <label><b>Group :</b></label>
                    <input type="text" name="consumerGroup"
                        value="<%=ServletUtility.getParameter("consumerGroup", request)%>">

                    &emsp;

                    <label><b>Status :</b></label>
                    <input type="text" name="status"
                        value="<%=ServletUtility.getParameter("status", request)%>">

                    &emsp;

                    <input type="submit" name="operation" value="<%=ConsumerListCtl.OP_SEARCH%>">
                    <input type="submit" name="operation" value="<%=ConsumerListCtl.OP_RESET%>">

                </td>
            </tr>
        </table>

        <br>

        <!-- TABLE -->
        <table border="1" style="width: 100%; border: groove;">

            <tr style="background-color: #e1e6f1e3;">
                <th width="5%"><input type="checkbox" id="selectall" /></th>
                <th width="5%">S.No</th>
                <th width="15%">Code</th>
                <th width="20%">Group</th>
                <th width="30%">Topic Name</th>
                <th width="15%">Status</th>
                <th width="10%">Edit</th>
            </tr>

            <%
                while (it.hasNext()) {
                    bean = it.next();
            %>

            <tr>
                <td align="center">
                    <input type="checkbox" class="case" name="ids"
                           value="<%=bean.getConsumerId()%>">
                </td>

                <td align="center"><%=index++%></td>
                <td align="center"><%=bean.getConsumerCode()%></td>
                <td align="center"><%=bean.getConsumerGroup()%></td>
                <td align="center"><%=bean.getTopicName()%></td>
                <td align="center"><%=bean.getStatus()%></td>

                <td align="center">
                    <a href="ConsumerCtl?id=<%=bean.getConsumerId()%>">Edit</a>
                </td>
            </tr>

            <%
                }
            %>

        </table>

        <!-- BUTTONS -->
        <table style="width: 100%">
            <tr>

                <td style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=ConsumerListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>>
                </td>

                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=ConsumerListCtl.OP_NEW%>">
                </td>

                <td align="center" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=ConsumerListCtl.OP_DELETE%>">
                </td>

                <td align="right" style="width: 25%">
                    <input type="submit" name="operation"
                        value="<%=ConsumerListCtl.OP_NEXT%>"
                        <%=nextPageSize != 0 ? "" : "disabled"%>>
                </td>

            </tr>
        </table>

        <%
            } else {
        %>

        <!-- NO RECORD -->
        <table>
            <tr>
                <td>
                    <input type="submit" name="operation"
                        value="<%=ConsumerListCtl.OP_BACK%>">
                </td>
            </tr>
        </table>

        <%
            }
        %>

    </form>

</div>

</body>
</html>