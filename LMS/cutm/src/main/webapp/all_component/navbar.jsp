<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm p-3">
    <a class="navbar-brand font-weight-bold" href="index.jsp">
        <i class="fas fa-briefcase"></i> LMS
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" 
            aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarContent">
        <ul class="navbar-nav me-auto">
            <!-- Home Link -->
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Home</a>
            </li>

            <!-- Admin Links -->
            <c:if test="${userobj.role eq 'admin'}">
                <li class="nav-item"><a class="nav-link" href="add_job.jsp"><i class="fas fa-plus-circle"></i> Post Job</a></li>
                <li class="nav-item"><a class="nav-link" href="view_job.jsp"><i class="fas fa-eye"></i> View Jobs</a></li>
                <li class="nav-item"><a class="nav-link" href="admin_applications.jsp"><i class="fas fa-clipboard-list"></i> Applications</a></li>
            </c:if>

            <!-- User Links -->
            <c:if test="${userobj.role eq 'user'}">
                <li class="nav-item"><a class="nav-link" href="home.jsp"><i class="fas fa-search"></i> Job Listings</a></li>
                <li class="nav-item"><a class="nav-link" href="my_applications.jsp"><i class="fas fa-clipboard-list"></i> My Applications</a></li>
            </c:if>
        </ul>

        <!-- User Profile and Auth Buttons -->
        <div class="d-flex">
            <!-- Profile and Logout for Admin/User -->
            <c:if test="${userobj.role eq 'admin' || userobj.role eq 'user'}">
                <a href="#" class="nav-link btn btn-light mx-2" data-bs-toggle="modal" data-bs-target="#profile-modal">
                    <i class="fas fa-user"></i> ${userobj.name}
                </a>
                <a href="logout" class="nav-link btn btn-outline-dark mx-2"><i class="fas fa-sign-out-alt"></i> Logout</a>
            </c:if>

            <!-- Login and Signup for Guests -->
            <c:if test="${empty userobj}">
                <a href="login.jsp" class="nav-link btn btn-outline-dark mx-2"><i class="fas fa-sign-in-alt"></i> Login</a>
                <a href="signup.jsp" class="nav-link btn btn-light mx-2"><i class="fas fa-user-plus"></i> Signup</a>
            </c:if>
        </div>
    </div>
</nav>

<!-- Profile Modal -->
<c:if test="${not empty userobj}">
    <div class="modal fade" id="profile-modal" tabindex="-1" aria-labelledby="profile-modal-label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content rounded">
                <div class="modal-header bg-light text-dark">
                    <h5 class="modal-title" id="profile-modal-label">Profile</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body text-center">
                    <i class="fas fa-user-circle fa-4x mb-3"></i>
                    <h5>${userobj.name}</h5>
                    <table class="table table-bordered mt-3">
                        <tbody>
                            <tr><th>Email</th><td>${userobj.email}</td></tr>
                            <tr><th>Role</th><td>${userobj.role}</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <a href="edit_profile.jsp" class="btn btn-primary">Edit Profile</a>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Custom Styles -->
<style>
    .navbar {
        transition: background-color 0.3s ease;
    }
    .navbar.scrolled {
        background-color: #f8f9fa !important;
    }
    .nav-link:hover {
        color: #007bff !important;
        background-color: rgba(0, 123, 255, 0.1);
        border-radius: 20px;
    }
    .modal-header { border-bottom: none; background-color: #f8f9fa; }
    .btn-rounded { border-radius: 20px; }
    .modal-content { border-radius: 15px; }
</style>

<!-- JavaScript for Scroll Effect -->
<script>
    window.addEventListener('scroll', function() {
        const navbar = document.querySelector('.navbar');
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    });
</script>

<!-- Bootstrap and FontAwesome -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
